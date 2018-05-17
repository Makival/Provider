package com.korotkevich.provider.repository;

import java.util.List;

import com.korotkevich.provider.exception.RepositoryException;
import com.korotkevich.provider.specification.Specification;

/**
 * Declaration of layer that interacts with data storage
 * @author Korotkevich Kirill 2018-05-10
 *
 * @param <T>
 */
public interface Repository<T> {
	/**
	 * Adds entity to data storage
	 * @param item incoming item
	 * @throws RepositoryException if problems occured while adding item
	 */
    void add(T item) throws RepositoryException;

    /**
     *  Updates data in data storage
     * @param specifications incoming Specification which describes interaction with data storage 
     * @throws RepositoryException if problems occured while updating item
     */
    void update(Specification... specifications) throws RepositoryException;

    /**
     * Removes data frome data storage
     * @param item incoming item
     * @throws RepositoryException if problems occured while removing item
     */
    void remove(T item) throws RepositoryException;

    /**
     * Searches data according to incoming specification
     * @param specification incoming Specification which describes interaction with data storage 
     * @return list with items according to specification
     * @throws RepositoryException if problems occured while searching item
     */
    List<T> query(Specification specification) throws RepositoryException;
}
