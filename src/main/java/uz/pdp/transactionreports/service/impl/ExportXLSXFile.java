package uz.pdp.transactionreports.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.TransactionExpenseDto;
import uz.pdp.transactionreports.dto.TransactionIncomeDto;
import uz.pdp.transactionreports.entity.Transaction;
import uz.pdp.transactionreports.service.TransactionService;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class ExportXLSXFile {

    private final TransactionService transactionService;

    public ExportXLSXFile(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Transactions");
    Path UPLOAD_DIR = Path.of("/home/user/product_photo/");
    File uploadDir = UPLOAD_DIR.toFile();
    Row headerRow = sheet.createRow(0);
    Row row;
    String[] columnsForAll = {"ID", "Transaction Category", "Expense category", "Amount", "Currency", "Customer Phone Number", "Transaction Date", "Description", "Attachment ID", "Transaction Status"};
    String[] columnsForAllCompleted = {"ID", "Transaction Category", "Expense category", "Amount", "Currency", "Customer Phone Number", "Transaction Date", "Description", "Attachment ID", "Transaction Status"};
    String[] columnsForAllIncomes = {"Transaction Category", "Amount", "Currency", "Customer Phone Number", "Transaction Date", "Description", "Attachment ID"};
    String[] columnsForAllExpenses = {"Expense category", "Amount", "Currency", "Transaction Date", "Description", "Attachment ID"};

    public String exportAllIncomesToXLSXFile() throws IOException {
        for (int i = 0; i < columnsForAllIncomes.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnsForAllIncomes[i]);
        }

        List<TransactionIncomeDto> transactions = transactionService.getAllIncomes();
        for (int i = 0; i < transactions.size(); i++) {
            row = sheet.createRow(i + 1);
            row.createCell(1).setCellValue(String.valueOf(transactions.get(i).getTransactionCategory()));
            row.createCell(2).setCellValue(String.valueOf(transactions.get(i).getAmount()));
            row.createCell(3).setCellValue(String.valueOf(transactions.get(i).getCurrency()));
            row.createCell(4).setCellValue(transactions.get(i).getCustomerPhoneNumber());
            row.createCell(5).setCellValue(transactions.get(i).getTransactionDate());
            row.createCell(6).setCellValue(transactions.get(i).getDescription());
            row.createCell(7).setCellValue(String.valueOf(transactions.get(i).getAttachmentId()));
        }

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Path outputFilePath = UPLOAD_DIR.resolve("reports.xlsx");
        try (FileOutputStream out = new FileOutputStream(outputFilePath.toFile())) {
                workbook.write(out);
        }

        workbook.close();
        return outputFilePath.toString();
    }


    public String exportAllExpensesToXLSXFile() throws IOException {

        for (int i = 0; i < columnsForAllExpenses.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnsForAllExpenses[i]);
        }
        List<TransactionExpenseDto> transactions = transactionService.getAllExpenses();
        for (int i = 0; i < transactions.size(); i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(String.valueOf(transactions.get(i).getExpenseCategory()));
            row.createCell(1).setCellValue(String.valueOf(transactions.get(i).getAmount()));
            row.createCell(2).setCellValue(String.valueOf(transactions.get(i).getCurrency()));
            row.createCell(3).setCellValue(transactions.get(i).getTransactionDate());
            row.createCell(4).setCellValue(transactions.get(i).getDescription());
            row.createCell(5).setCellValue(String.valueOf(transactions.get(i).getAttachmentId()));
        }

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Path outputFilePath = UPLOAD_DIR.resolve("reports.xlsx");
        try (FileOutputStream out = new FileOutputStream(outputFilePath.toFile())) {
            workbook.write(out);
        }

        workbook.close();
        return outputFilePath.toString();
    }

    public String exportAllCompletedToXLSXFile() throws IOException {

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columnsForAllCompleted.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnsForAllCompleted[i]);
        }
        List<Transaction> transactions = transactionService.getAllCompleted();
        for (int i = 0; i < transactions.size(); i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(String.valueOf(transactions.get(i).getId()));
            row.createCell(1).setCellValue(String.valueOf(transactions.get(i).getTransactionCategory()));
            row.createCell(2).setCellValue(String.valueOf(transactions.get(i).getExpenseCategory()));
            row.createCell(3).setCellValue(String.valueOf(transactions.get(i).getAmount()));
            row.createCell(4).setCellValue(String.valueOf(transactions.get(i).getCurrency()));
            row.createCell(5).setCellValue(transactions.get(i).getCustomer().getPhoneNumber());
            row.createCell(6).setCellValue(transactions.get(i).getTransactionDate());
            row.createCell(7).setCellValue(transactions.get(i).getDescription());
            row.createCell(8).setCellValue(String.valueOf(transactions.get(i).getAttachment().getId()));
        }

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Path outputFilePath = UPLOAD_DIR.resolve("reports.xlsx");
        try (FileOutputStream out = new FileOutputStream(outputFilePath.toFile())) {
            workbook.write(out);
        }

        workbook.close();
        return outputFilePath.toString();
    }



    public String exportAllReportsToXLSXFile() throws IOException {

        for (int i = 0; i < columnsForAll.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnsForAll[i]);
        }

        List<Transaction> transactions = transactionService.getAll();
        for (int i = 0; i < transactions.size(); i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(String.valueOf(transactions.get(i).getId()));
            row.createCell(1).setCellValue(String.valueOf(transactions.get(i).getTransactionCategory()));
            row.createCell(2).setCellValue(String.valueOf(transactions.get(i).getExpenseCategory()));
            row.createCell(3).setCellValue(String.valueOf(transactions.get(i).getAmount()));
            row.createCell(4).setCellValue(String.valueOf(transactions.get(i).getCurrency()));
            row.createCell(5).setCellValue(transactions.get(i).getCustomer().getPhoneNumber());
            row.createCell(6).setCellValue(transactions.get(i).getTransactionDate());
            row.createCell(7).setCellValue(transactions.get(i).getDescription());
            row.createCell(8).setCellValue(String.valueOf(transactions.get(i).getAttachment().getId()));
            row.createCell(9).setCellValue(String.valueOf(transactions.get(i).getTransactionStatus()));
        }

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Path outputFilePath = UPLOAD_DIR.resolve("reports.xlsx");
        try (FileOutputStream out = new FileOutputStream(outputFilePath.toFile())) {
            workbook.write(out);
        }

        workbook.close();
        return outputFilePath.toString();
    }
}
