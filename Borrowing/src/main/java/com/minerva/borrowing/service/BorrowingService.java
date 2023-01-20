package com.minerva.borrowing.service;

import java.util.List;

import com.minerva.borrowing.entities.Borrowing;
import java.util.Date;

public interface BorrowingService {

	//GET
		Borrowing getBorrowingById(String id);
		
		List<Borrowing>getAllBorrowing();
		
		List<Borrowing>getBorrowingByIdLibri(String id_libri);
		
		List<Borrowing>getBorrowingByIdClienti(String id_clienti);
		
		List<Borrowing>getBorrowingDataScadenza(Date data_scadenza);
		
		List<Borrowing>getBorrowingDataInzio(Date data_inizio);
	
	
	//POST
		Borrowing addBorrowing(Borrowing b);
	
	//PUT
		Borrowing updateBorrowing(Borrowing b);
	
	//DELETE
		boolean deleteBorrowingById(String id);
	
	

}
