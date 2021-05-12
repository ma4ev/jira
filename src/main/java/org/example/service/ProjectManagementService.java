package org.example.service;

public interface ProjectManagementService {

    void addUser(long projectId, long userId);

    void removeUser(long projectId, long userId);
}
