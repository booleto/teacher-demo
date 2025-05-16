package com.tophat.teacherdemo.entity.answer;

/**
 * Interface for generic Answer entity.
 * <p>
 * All answer types must implement this interface.
 */
public interface Answer {

    /**
     * Checks whether another {@link Answer} is equivalent to this answer.
     * @param answer the other answer to compare against.
     * @return True if the answers are equivalent, False if otherwise.
     */
    boolean isEquals(Answer answer);
}