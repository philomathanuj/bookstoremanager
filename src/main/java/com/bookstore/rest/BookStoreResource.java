package com.bookstore.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.bookstore.RestResponse;
import com.bookstore.entity.Book;
import com.bookstore.exception.ServiceException;
import com.bookstore.service.BookStoreService;

@Component
@Path("/bookstore")
public class BookStoreResource{
	
	@Autowired
	private BookStoreService bookStoreService;
	
	@POST
	@Path("purchase/add")
	@Produces("application/json")
    @Consumes("application/json")
	public Response addPurchase(@RequestBody Book book){
		try {
			this.bookStoreService.addPurchase(book);
		} catch (ServiceException e) {
			return RestResponse.notOk("Failure to save purchase details",e.getMessage());
		}
		return RestResponse.ok("Purchase details added successfully",1);	
	}
	
	
	@GET
	@Path("/purchase/view/{id}")
	@Produces("application/json")
	public Book viewPurchase(@PathParam(value = "id") Integer id){
		Book book = null;
		try {
			System.out.println(id);
			System.out.println("book id is above");
			book = this.bookStoreService.getBookByID(id);
			System.out.println(book.toString());
		} catch (ServiceException e) {
			return null;
		}
		return book;	
	}
	
	@GET
	@Path("/purchase/view/all")
	@Produces("application/json")
	public Response viewAllPurchases(){
		List<Book> books = null;
		try {
			books = this.bookStoreService.getAllBooks();
		} catch (ServiceException e) {
			return RestResponse.notOk("Failure to save item",e.getMessage());
		}
		return RestResponse.ok("data",books,books.size());
	}

}
