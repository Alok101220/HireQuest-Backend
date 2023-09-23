/**
 * 
 */
package com.alok91340.gethired.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aloksingh
 *
 */
@Service
public class PresenceService {
    private final Map<String, Set<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    public void addUserSession(String userId, WebSocketSession session) {
        userSessions.computeIfAbsent(userId, k -> new HashSet<>()).add(session);
    }

    public void removeUserSession(String userId) {
        userSessions.remove(userId);
    }

    public Set<WebSocketSession> getSessionsForUser(String userId) {
        return userSessions.getOrDefault(userId, Collections.emptySet());
    }
}
