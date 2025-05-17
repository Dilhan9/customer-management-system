package com.assignment.customermanagement.service;

import com.assignment.customermanagement.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerBulkService {

    @PersistenceContext
    private EntityManager entityManager;

    private static final int BATCH_SIZE = 50;

    @Async
    @Transactional
    public CompletableFuture<Void> processExcel(MultipartFile file) {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            int count = 0;

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Customer customer = mapRowToCustomer(row);

                entityManager.persist(customer);

                if (++count % BATCH_SIZE == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }

            entityManager.flush();
            entityManager.clear();

        } catch (Exception e) {
            throw new RuntimeException("Failed to process Excel file", e);
        }

        return CompletableFuture.completedFuture(null);
    }

    private Customer mapRowToCustomer(Row row) {
        Customer customer = new Customer();


        customer.setName(getCellStringValue(row.getCell(0)));

        Cell dobCell = row.getCell(1);
        if (dobCell != null && dobCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dobCell)) {
            customer.setDob(dobCell.getLocalDateTimeCellValue().toLocalDate());
        }

        customer.setNic(getCellStringValue(row.getCell(2)));

        String mobiles = getCellStringValue(row.getCell(3));
        if (mobiles != null && !mobiles.isEmpty()) {
            String[] mobileArray = mobiles.split(",");
            for (String m : mobileArray) {
                customer.getMobileNumbers().add(m.trim());
            }
        }


        com.assignment.customermanagement.entity.Address address = new com.assignment.customermanagement.entity.Address();
        address.setAddressLine1(getCellStringValue(row.getCell(4)));
        address.setAddressLine2(getCellStringValue(row.getCell(5)));
        address.setCityCode(getCellStringValue(row.getCell(6)));
        address.setCountryCode(getCellStringValue(row.getCell(7)));

        customer.getAddresses().add(address);


        return customer;
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((long) cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return Boolean.toString(cell.getBooleanCellValue());
        }
        return null;
    }
}