package jack.tools.ndsk_sql_2.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.cglib.transform.impl.UndeclaredThrowableStrategy;
import org.springframework.stereotype.Service;

import javax.swing.text.TabExpander;
import java.io.*;

@Service("ndskSqlService")
public class NdskSqlService {

    public final static String FILE_SUFFIX_XLSX = ".xlsx";
    public final static String FILE_SUFFIX_SQL = ".sql";
    public final static String SQL_START_TRANSACTION = "START TRANSACTION;\n\n";
    public final static String SQL_COMMIT_TRANSACTION = "\nCOMMIT;\n";

    /**
     * 1. transform .xlsx to SQL string
     * 2. text cell value to 'value'
     * 3. numeric cell value to value
     * 4. cell value NULL to NULL
     * 5. date cell value to  // not handled currently
     * @param sourceInputStream
     * @param tableName
     * @return
     */
    public void xlsxToSql(InputStream sourceInputStream, String tableName, String sqlFileName) {
        StringBuilder sqlSb = new StringBuilder(SQL_START_TRANSACTION); // sb.append("INSERT INTO `city` (`");
        StringBuilder headerSb = new StringBuilder();
        Workbook wb = null;
        Sheet sheet = null;

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(sqlFileName));

            wb = WorkbookFactory.create(sourceInputStream);
            sheet = wb.getSheetAt(0);
            // append header
            Row headerRow = sheet.getRow(0);
            for (int i = 1; i < headerRow.getLastCellNum(); i++) {// Row.getLastCellNum() : start from 1
                headerSb.append("`" + headerRow.getCell(i).getStringCellValue() + "`");
                if (i != headerRow.getLastCellNum() - 1) {
                    headerSb.append(", ");
                }
            }
            sqlSb.append("INSERT INTO `" + tableName + "` (" + headerSb.toString() + ") VALUES" + "\n");
            bw.write(sqlSb.toString());
            System.out.println(sqlSb);

            // append row sql
            StringBuilder rowSb = null;
            Row itemRow = null;
            String rowItemStr = null;
            // iterate rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //System.out.println("lastRowNumber = " + sheet.getLastRowNum()); // The first row index is 0, getLastRowNum() is total number - 1
                rowSb = new StringBuilder("(");
                itemRow = sheet.getRow(i);
                //iterate cells in a row
                for (int j = 1; j < itemRow.getLastCellNum(); j++) {
                    //System.out.println("lastCellNumber = " + itemRow.getLastCellNum()); // The first cell index is 0, getLastCellNum is total number
                    Cell itemCell = itemRow.getCell(j);
                    //System.out.println(itemCell.getCellTypeEnum());
                    if (CellType.STRING.equals(itemCell.getCellTypeEnum())) {
                        rowSb.append("'" + itemCell.getStringCellValue() + "'");
                    } else if (CellType.NUMERIC.equals(itemCell.getCellTypeEnum())) {
                        rowSb.append(new Double(itemCell.getNumericCellValue()).intValue());
                    }
                    // if it is not the last cell
                    if (j != itemRow.getLastCellNum() - 1) {
                        rowSb.append(", ");
                    }
                }
                // whether is it the last row or not
                if (i != sheet.getLastRowNum()) {
                    rowItemStr = rowSb.append("),\n").toString();
                } else {
                    rowItemStr = rowSb.append(");\n").toString();
                }
                // handle NULL
                rowItemStr = rowItemStr.replaceAll("'NULL'", "NULL");
                rowItemStr = rowItemStr.replaceAll("'null'", "NULL");

                bw.write(rowItemStr);
//                sqlSb.append(rowItemStr);
            }
//            sqlSb.append(SQL_COMMIT_TRANSACTION);
            bw.write(SQL_COMMIT_TRANSACTION);
//            System.out.println(sqlSb);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void stringToSqlFile(String source, String sqlFileName) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(sqlFileName));
            bw.write(source);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @return
     */
    private String getTimestamp() {
        String timestamp = null;

        return timestamp;
    }

}