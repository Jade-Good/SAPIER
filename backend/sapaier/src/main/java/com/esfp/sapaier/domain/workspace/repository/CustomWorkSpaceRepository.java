package com.esfp.sapaier.domain.workspace.repository;

public interface CustomWorkSpaceRepository {

    void deleteUserPermissionByWorkspaceIdxAndUuId(String workspaceidx, String uuId);
}
