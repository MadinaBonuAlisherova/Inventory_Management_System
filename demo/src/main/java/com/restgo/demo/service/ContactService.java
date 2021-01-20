package com.restgo.demo.service;

import com.restgo.demo.model.Contact;
import com.restgo.demo.model.Result;
import com.restgo.demo.util.DB;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    @Autowired
    HikariDataSource dataSource;

    private String newContact = "INSERT INTO CONTACT(name, quantity, size, brand,description, selling_price ) VALUES(?,?,?,?,?,?)";
    private String allContacts = "SELECT * FROM CONTACT";
    private String contactById = "SELECT * FROM CONTACT WHERE ID = ?";
    private String updateContact = "UPDATE CONTACT SET name = ? , quantity = ? , size =?, brand =?, description =?, selling_price=? WHERE ID = ?";
    private String deleteContact = "DELETE FROM CONTACT WHERE id = ?";
    private String checkContact = "SELECT * FROM CONTACT WHERE name = ? and  quantity = ? and size =? and  brand =? and description =? and selling_price=?";

    Connection connection;
    PreparedStatement statement;
    ResultSet resultSet;

    // get all Contacts
    public List<Contact> getAllContact() {
        List<Contact> contacts = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(allContacts);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                contacts.add(new Contact(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("quantity"),
                        resultSet.getDouble("size"),
                        resultSet.getString("brand"),
                        resultSet.getString("description"),
                        resultSet.getDouble("selling_price")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.done(resultSet);
            DB.done(statement);
            DB.done(connection);
        }
        return contacts;
    }

    // get Contact By Id
    public Contact getContactById(Integer id) {
        Contact contact = new Contact();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(contactById);
            statement.setInt(1, id);
            statement.execute();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                contact.setId(resultSet.getInt("id"));
                contact.setName(resultSet.getString("name"));
                contact.setQuantity(resultSet.getDouble("quantity"));
                contact.setSize(resultSet.getDouble("size"));
                contact.setBrand(resultSet.getString("brand"));
                contact.setDescription(resultSet.getString("description"));
                contact.setSelling_price(resultSet.getDouble("selling_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.done(resultSet);
            DB.done(statement);
            DB.done(connection);
        }
        return contact;
    }

    // save new Contact
    public Result saveContact(Contact contact, BindingResult bindingResult) {

        Result result = new Result();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(newContact);
            statement.setString(1, contact.getName());
            statement.setDouble(2, (double)contact.getQuantity());
            statement.setDouble(3, (double)contact.getSize());
            statement.setString(4, contact.getBrand());
            statement.setString(5, contact.getDescription());
            statement.setDouble(6, (double)contact.getSelling_price());
            statement.execute();
            result.setMessage("Successfully Saved");
            result.setSuccess(true);
        } catch (SQLException e) {
            e.printStackTrace();
            result.setMessage(" Error in saving contact else if!");
            result.setSuccess(false);
        } finally {
            DB.done(resultSet);
            DB.done(statement);
            DB.done(connection);
        }
        return result;
    }

    // edit Contact
    public Result editContact(Integer id, Contact contact, BindingResult bindingResult) {
        Result result = new Result();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateContact);
            statement.setString(1, contact.getName());
            statement.setDouble(2, contact.getQuantity());
            statement.setDouble(3, contact.getSize());
            statement.setString(4, contact.getBrand());
            statement.setString(5, contact.getDescription());
            statement.setDouble(6, contact.getSelling_price());
            statement.setInt(7, id);
            statement.execute();
            result.setMessage("Successfully Saved");
            result.setSuccess(true);
        } catch (SQLException e) {
            e.printStackTrace();
            result.setMessage("Error in saving contact else if!");
            result.setSuccess(false);
        } finally {
            DB.done(resultSet);
            DB.done(statement);
            DB.done(connection);
        }
        return result;
    }

    // delete contact
    public Result deleteById(Integer id) {
        Result result = new Result();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteContact);
            statement.setInt(1, id);
            statement.execute();
            result.setMessage("Successfully Deleted");
            result.setSuccess(true);
        } catch (SQLException e) {
            e.printStackTrace();
            result.setMessage("Error");
            result.setSuccess(false);
        } finally {
            DB.done(resultSet);
            DB.done(statement);
            DB.done(connection);
        }
        return result;
    }
    private boolean validateContactFromDB(Contact contact){
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(checkContact);
            statement.setString(1,contact.getName());
            statement.setDouble(2, contact.getQuantity());
            statement.setDouble(3, contact.getSize());
            statement.setString(4, contact.getBrand());
            statement.setString(5, contact.getDescription());
            statement.setDouble(6, contact.getSelling_price());
            resultSet = statement.getResultSet();
            if (resultSet.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
