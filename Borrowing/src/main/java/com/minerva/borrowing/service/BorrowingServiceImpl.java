package com.minerva.borrowing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.minerva.borrowing.entities.Borrowing;
import com.minerva.borrowing.repos.BorrowingRepo;
import org.springframework.web.server.ResponseStatusException;


@Service
public class BorrowingServiceImpl implements BorrowingService{
	@Autowired
	private final BorrowingRepo repo;
	private final List<Borrowing> empty = new ArrayList<>();

	@Autowired
	public BorrowingServiceImpl(BorrowingRepo repo) {
		this.repo = repo;
	}
	@Override
	public Borrowing getBorrowingById(String id) {
		return repo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find borrowing with id: " + id));
	}

	@Override
	public List<Borrowing> getAllBorrowing() {
		List<Borrowing> borrowing = repo.findAll();
		
		if (borrowing.isEmpty()) {
            return empty;
        }
		return borrowing;
	}

	@Override
	public List<Borrowing> getBorrowingByIdLibri(String id_libri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Borrowing> getBorrowingByIdClienti(String id_clienti) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Borrowing> getBorrowingDataScadenza(Date data_scadenza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Borrowing> getBorrowingDataInzio(Date data_inizio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Borrowing addBorrowing(Borrowing newBorrowing) {
		return repo.save(newBorrowing);
	}

	@Override
	public Borrowing updateBorrowing(Borrowing updatedBorrowing) {
		return repo.save(updatedBorrowing);
	}

	@Override
	public boolean deleteBorrowingById(String id) {
		if( !repo.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find borrowing with id: " + id);
        repo.deleteById(id);

		return repo.findById(id).isEmpty();
	}



}
	
	
	
