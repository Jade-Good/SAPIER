pipeline{
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
					sh 'docker-compose -f docker-compose-prod.yml down'
					// sh 'docker-compose -f /var/jenkins_home/workspace/test3/docker-compose-prod.yml down'
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
				//TODO : 해당 부분 쉘스크립트 작성하기

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
				sh 'chmod +x /var/jenkins_home/workspace/test3/start-prod.sh'
			}
		}
		stage('Execute start-prod.sh Script'){
			steps{
				sh '/var/jenkins_home/workspace/test3/start-prod.sh'
			}
		}
	}
}
