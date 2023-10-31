pipeline{
	//docker hub credentials 정보
	environment{
		repository = "wonjunchun/jenkins"
		DOCKERHUB_CREDENTIALS = credentials('dockerhub-jenkins')
		dockerImage = ''
	}
	agent any
	stages{
		//준비단계 -> Merge된 git파일을 webhook에서 감지하여 가져옴
		stage('Prepare'){
			steps{
				sh 'echo "Clonning Repository"'
				git branch: 'develop',
					url: 'https://lab.ssafy.com/s09-final/S09P31B301.git',
					credentialsId: '5323180c-e521-47db-bf61-b04d960593dc'
			}
			post{
				success{
					sh 'echo "Successfully Cloned Repository"'
				}
				failure{
					sh 'echo "Fail Cloned Repository"'
				}
			}
		}
		stage('Docker stop'){
			steps{
				dir('backend'){
					sh 'echo "Docker Container Stop"'
					//도커 컴포즈 다운
					// sh 'curl -L https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose'
					// sh 'chmod -R 777 /usr/local/bin'
					sh 'curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose'
					//해당 도커 컴포즈 다운한 경로로 권한 설정
					// sh 'chmod -R 777 /usr/local/bin'
					sh 'chmod +x /usr/local/bin/docker-compose'
					//기존 백그라운드에 돌아가던 컨테이너 중지
					// sh 'docker-compose -f docker-compose-prod.yml down'
					sh 'docker-compose -f /var/jenkins_home/workspace/sapier-pipeline/docker-compose-prod.yml down'
				}
			}
			post{
				failure{
					sh 'echo "Docker Fail"'
				}
			}
		}
		//정지된 도커 컨테이너 삭제
		stage('RM Docker'){
			steps{
				sh 'echo "Remove Docker"'
				//정지된 도커 컨테이너 찾아서 컨테이너 ID로 삭제
				//spr로 시작하는 컨테이너 전부 삭제
				sh '''
					result=$( docker container ls -a --filter "name=spr*" -q)
					if [ -n "$result" ]
					then
						docker rm $(docker container ls -a --filter "name=ttp*" -q)
					else
						echo "No such containers"
					fi
				'''
				//spr로 시작하는 이미지 찾아서 삭제
				sh '''
					result=$( docker images -f "reference=spr*" -q )
					if [ -n "$result" ]
					then
						docker rmi -f $(docker images -f "reference=spr*" -q)
					else
						echo "No such container images"
					fi
				'''
				//안쓰는 이미지 -> <none> 태그 찾아서 삭제함
				sh '''
					result=$(docker images -f "dangling=true" -q)
					if [ -n "$result" ]
					then
						docker rmi -f $(docker images -f "dangling=true" -q)
					else
						echo "No such container images"
					fi
				'''
			}
			post{
				failure{
					sh 'echo "Remove Fail"'
				}
			}
		}
		//프로젝트 내에 있는 start-prod.sh 실행
		stage('Set Permissions'){
			steps{
				//스크립트 파일에 실행권한 추가
				sh 'chmod +x /var/jenkins_home/workspace/sapier-pipeline/start-prod.sh'
			}
		}
		stage('Execute start-prod.sh Script'){
			steps{
				//docker hub login
				sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
				sh '/var/jenkins_home/workspace/sapier-pipeline/start-prod.sh'
			}
		}
	}
}
