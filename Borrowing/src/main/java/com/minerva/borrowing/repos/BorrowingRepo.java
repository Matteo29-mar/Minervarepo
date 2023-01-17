package com.minerva.borrowing.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.minerva.borrowing.entities.Borrowing;

@Repository
public interface BorrowingRepo extends MongoRepository<Borrowing, String> {

	
}
