package org.example.service;

public interface UserManagementProjectService {

    void addUser(long projectId, long userId);

    void removeUser(long projectId, long userId);
}
