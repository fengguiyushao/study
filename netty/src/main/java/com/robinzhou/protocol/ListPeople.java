package com.robinzhou.protocol;

import com.robinzhou.protocol.AddressBookProtos.AddressBook;
import com.robinzhou.protocol.AddressBookProtos.Person;

import java.io.FileInputStream;

/**
 * Created by robinzhou on 2017/4/26.
 */
public class ListPeople {
    static void Print(AddressBook addressBook) {
        for (Person person : addressBook.getPeopleList()) {
            System.out.println("Person ID: " + person.getId());
            System.out.println("    Name:" + person.getName());
            if(person.hasEmail()) {
                System.out.println("    E-mail address: " + person.getEmail());
            }

            for (Person.PhoneNumber phoneNumber : person.getPhonesList()) {
                switch (phoneNumber.getType()) {
                    case MOBILE:
                        System.out.print("    Mobile phone#: ");
                        break;
                    case HOME:
                        System.out.print("    Home phone#:");
                        break;
                    case WORK:
                        System.out.print("    Work phone#:");
                        break;
                }
                System.out.println(phoneNumber.getNumber());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String filename = "D:\\Workspace\\study\\netty\\src\\main\\java\\com\\robinzhou\\protocol\\addressBook.txt";
        AddressBook addressBook = AddressBook.parseFrom(new FileInputStream(filename));
        Print(addressBook);
    }
}
