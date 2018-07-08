package jack.tools.ndsk_sql_2.web.controller;

import jack.tools.ndsk_sql_2.service.NdskSqlService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class NdskSqlController {
    public final static String TEMP_FILE_PATH = "src/main/resources/static/temp/";

    @Autowired
    private NdskSqlService ndskSqlService;

    @GetMapping("/fileUpload")
    public String staticNdskSql() {
        return "fileUpload";
    }

    @PostMapping("/excelToSql")
    public ResponseEntity<byte[]> ndskSql(@RequestParam(name = "file_ndsk_excel") /*CommonsMultipartFile*/ MultipartFile sourceFile,
                                  @RequestParam(name = "tableName") @NotNull String tableName) throws IOException {
//        ModelAndView modelAndView = new ModelAndView();

        if (sourceFile.getOriginalFilename().endsWith(NdskSqlService.FILE_SUFFIX_XLSX)) {
//            modelAndView.addObject("format", true);
//            System.out.println(sourceFile.getOriginalFilename());
            String sqlFileName = TEMP_FILE_PATH + tableName + "_" + System.currentTimeMillis() + NdskSqlService.FILE_SUFFIX_SQL;
            ndskSqlService.xlsxToSql(sourceFile.getInputStream(), tableName, sqlFileName);

//            ndskSqlService.stringToSqlFile(sqlStr, sqlFileName);
            File sqlFile = new File(sqlFileName);
            if (sqlFile.exists()) {
//                modelAndView.setViewName(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "temp/" + sqlFile.getName());
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachement;filename=" + new File(sqlFileName).getName());
                httpHeaders.set(HttpHeaders.CONTENT_ENCODING, "utf-8");
                return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(sqlFile), httpHeaders, HttpStatus.OK);
            }
        } else {
//            modelAndView.addObject("format", false);
//            modelAndView.setViewName("fileUpload");
        }
        return null;
    }

    public static void main(String[] args) {
        File file = new File("src/main/resources/static/temp/web.sql");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
    }

}
