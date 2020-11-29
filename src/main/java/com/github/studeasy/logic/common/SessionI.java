package com.github.studeasy.logic.common;

/**
 * Read-only interface for the session
 *
 * The router can access the session to know the role of the user
 * and then give the user access to only what he is allowed
 */
public interface SessionI {
    /**
     * Check if the current user is an admin
     * @return true if it's an admin, false otherwise
     */
    boolean isAdmin();
    /**
     * Check if the current user is a studebt
     * @return true if it's a student, false otherwise
     */
    boolean isStudent();
    /**
     * Check if the current user is a partner
     * @return true if it's a partner, false otherwise
     */
    boolean isPartner();
}