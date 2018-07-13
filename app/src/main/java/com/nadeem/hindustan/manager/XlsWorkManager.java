package com.nadeem.hindustan.manager;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.database.AppDatabase;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.utils.Utils;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.work.Data;
import androidx.work.Worker;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.read.biff.PasswordException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class XlsWorkManager extends Worker {

    @NonNull
    @Override
    public WorkerResult doWork() {
        if (getInputData().getBoolean("IS_EXPORT", false))
            return exportToXls();
        else
            return importToDB(getInputData().getString("FILE_PATH", ""));
    }

    private WorkerResult importToDB(String filePath) {
        try {

            //file path
            File file = new File(filePath);
            Workbook workbook = Workbook.getWorkbook(file);
            Sheet[] sheets = workbook.getSheets();
            for (int sheetIndex = 0; sheetIndex < sheets.length; sheetIndex++) {
                insertSheetInDB(sheets[sheetIndex]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return WorkerResult.FAILURE;
        }
        return WorkerResult.SUCCESS;
    }

    private WorkerResult exportToXls() {
        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "MerchantDATA.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            WritableCellFormat format = new WritableCellFormat();
            format.setBackground(Colour.GREY_80_PERCENT);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet(Utils.parseDate(Calendar.getInstance().get(Calendar.YEAR) + "" + Calendar.getInstance().get(Calendar.MONTH) + "" + Calendar.getInstance().get(Calendar.YEAR) + Calendar.getInstance().get(Calendar.DATE), 11), 0);
            // column and row
            sheet.addCell(new Label(0, 0, "ID", format));
            sheet.addCell(new Label(1, 0, "BILL NO", format));
            sheet.addCell(new Label(2, 0, "NAME", format));
            sheet.addCell(new Label(3, 0, "MOBILE", format));
            sheet.addCell(new Label(4, 0, "DEBIT", format));
            sheet.addCell(new Label(5, 0, "DEBIT DATE", format));
            sheet.addCell(new Label(6, 0, "CREDIT", format));
            sheet.addCell(new Label(7, 0, "CREDIT DATE", format));
            List<Merchant> merchants = HindustanApplication.getInstance().getDatabase().getMerchantDao().getMerchantsList();
            for (int i = 0; i < merchants.size(); i++) {
                Merchant merchant = merchants.get(i);
                sheet.addCell(new Label(0, i + 1, "" + merchant.getId()));
                sheet.addCell(new Label(1, i + 1, "" + merchant.getBillNo()));
                sheet.addCell(new Label(2, i + 1, "" + merchant.getName()));
                sheet.addCell(new Label(3, i + 1, "" + merchant.getMobile()));
                sheet.addCell(new Label(4, i + 1, "" + merchant.getDebit()));
                sheet.addCell(new Label(5, i + 1, "" + merchant.getDebitDate()));
                sheet.addCell(new Label(6, i + 1, "" + merchant.getCredit()));
                sheet.addCell(new Label(7, i + 1, "" + merchant.getCreditDate()));
                //...set the output, and we're done!
                Data output = new Data.Builder()
                        .putInt("STATUS", i)
                        .build();
                setOutputData(output);
            }
            //closing workbook
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            return WorkerResult.FAILURE;
        }
        return WorkerResult.SUCCESS;
    }

    private void insertSheetInDB(Sheet sheet) {
        AppDatabase database = HindustanApplication.getInstance().getDatabase();
        int rowCount = sheet.getRows();
        int columnCount = sheet.getColumns();
        Cell[] headerCells = sheet.getRow(0);
        if (headerCells.length == 8 && headerCells[0].getContents().equalsIgnoreCase("ID")) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                Cell[] columns = sheet.getRow(rowIndex);
                try {
                    database.getMerchantDao().updateMerchant(new Merchant(Long.parseLong(columns[0].getContents()),
                            columns[1].getContents(),
                            columns[2].getContents(),
                            columns[3].getContents(),
                            Double.parseDouble(columns[4].getContents()),
                            Double.parseDouble(columns[5].getContents()),
                            Long.parseLong(columns[6].getContents()),
                            Long.parseLong(columns[7].getContents())));
                } catch (Throwable throwable) {

                }
            }
        } else if (headerCells.length == 7 && headerCells[0].getContents().equalsIgnoreCase("BILL NO")) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                Cell[] columns = sheet.getRow(rowIndex);
                try {
                    database.getMerchantDao().insertMerchant(new Merchant(columns[0].getContents(),
                            columns[1].getContents(),
                            columns[2].getContents(),
                            Double.parseDouble(columns[3].getContents()),
                            Double.parseDouble(columns[4].getContents()),
                            Long.parseLong(columns[5].getContents()),
                            Long.parseLong(columns[6].getContents())));
                } catch (Throwable throwable) {

                }
            }
        } else {
            Utils.showToast(sheet.getName() + " is not in valid format");
        }
    }
}
