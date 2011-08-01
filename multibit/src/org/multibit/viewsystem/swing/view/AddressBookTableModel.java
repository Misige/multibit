package org.multibit.viewsystem.swing.view;

import java.util.SortedSet;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.multibit.Localiser;
import org.multibit.model.AddressBook;
import org.multibit.model.AddressBookData;

public class AddressBookTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -937886012851116208L;

    private Vector<String> headers = new Vector<String>();

    private final String[] tableHeaderKeys = new String[] {
            "addressBookTableModel.labelColumnHeader", "addressBookTableModel.addressColumnHeader" };

    private AddressBook addressBook;
    private boolean isReceiving;

    public AddressBookTableModel(Localiser localiser, AddressBook addressBook, boolean isReceiving) {
        for (int j = 0; j < tableHeaderKeys.length; j++) {
            headers.add(localiser.getString(tableHeaderKeys[j]));
        }
        
        this.addressBook = addressBook;
        this.isReceiving = isReceiving;
    }

    public int getColumnCount() {
        return tableHeaderKeys.length;
    }

    public int getRowCount() {
        if (isReceiving) {
            return addressBook.getReceivingAddresses().size();
        } else {
            return addressBook.getSendingAddresses().size();
        }
    }

    public String getColumnName(int column) {
        return headers.get(column);
    }

    public Object getValueAt(int row, int column) {
        SortedSet<AddressBookData> addresses;
        if (isReceiving) {
            addresses = addressBook.getReceivingAddresses();
        } else {
            addresses = addressBook.getSendingAddresses();
        }

        AddressBookData[] addressesArray = (AddressBookData[]) addresses.toArray(new AddressBookData[addresses.size()]);
        AddressBookData addressBookData = null;
        if (row >= 0 && row < addresses.size()) {
            addressBookData = addressesArray[row];
        }
        
        if (addressBookData == null) {
            return null;
        }

        switch (column) {
        case 0:
            return addressBookData.getLabel();
        case 1:
            return addressBookData.getAddress();
        default:
            return null;
        }
    }

    /**
     * table model is read only
     */
    public void setValueAt(Object value, int row, int column) {
        throw new UnsupportedOperationException();
    }
}
