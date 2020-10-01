package Spr.web;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class Person{
    public String name;
    public int age;
    public Person(String name,int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
}

@Controller
@RequestMapping("/r")
public class ViewController {

    public static final String VIDEO = "/video";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String VIDEO_CONTENT = "video/";
    public static final int BYTE_RANGE = 1024;

    public ResponseEntity<byte[]> prepareContent(MultipartFile file) {
        long rangeStart = 0;
        byte[] data;
        Long fileSize;
        try {
            fileSize = file.getSize();
            return ResponseEntity.status(HttpStatus.OK)
                    .header(CONTENT_TYPE, VIDEO_CONTENT + Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1])
                    .header(CONTENT_LENGTH, String.valueOf(fileSize))
                    .body(readByteRange(file, rangeStart, fileSize - 1));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public byte[] readByteRange(MultipartFile file, long start, long end) throws IOException {
        try (InputStream inputStream = (file.getInputStream());
             ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[BYTE_RANGE];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }
            bufferedOutputStream.flush();
            byte[] result = new byte[(int) (end - start) + 1];
            System.out.print(Arrays.toString(data));
            System.out.print(data.length);
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
            return result;
        }
    }




    @ResponseBody
    @GetMapping("/{login}/{password}")
    public static String[] get(@PathVariable(required = true,name = "login") String login,@PathVariable(required = true,name = "password") String password) {
        return new String[]{login, password};

    }
    @ResponseBody
    @PostMapping("/")
    public static String[] post(@RequestParam(required = true,defaultValue = "a") String id) {
        return new String[]{id};
    }
    @ResponseBody
    @GetMapping("/ex")
    public static ResponseEntity<String> handle(){
        Map<String,List<String>> map = new HashMap<>();
        List<String> l = new LinkedList<>();
        l.add("arg1");
        l.add("arg2");
        map.put("JSON",l);
        return new ResponseEntity<String>("Hello", new LinkedMultiValueMap<String, String>(map), HttpStatus.CREATED);
    }
    @GetMapping("/template")
    public static String temp(Model model, HttpServletRequest request){
        model.addAttribute("person",new Person("XYZ",15));
        return "template";
    }
    @GetMapping("/file")
    public static String filetemp(Model model){
        return "FileTemp";
    }
    @ResponseBody
    @PostMapping("/file")
    public static String posttemp(@RequestParam("file")MultipartFile file,HttpServletRequest request,RequestEntity<String> entity){
        return file.getOriginalFilename() +" + " + request.getParameter("text") + " + " + request.getSession(true);
    }
    @GetMapping("/body")
    public static String getBody(Model model){
        return "Templ";
    }
    @ResponseBody
    @PostMapping("/body")
    public static String postBody(@RequestBody String body, RequestEntity<String> requestEntity){
        return body +"   "+ requestEntity.getBody();
    }
    @ResponseBody
    @GetMapping("/request/servlet")
    public static String requestServlet(HttpServletRequest request){
        return String.valueOf(request.getServerPort());
    }
    @ResponseBody
    @GetMapping("/request/entity")
    public static String requestEntity(RequestEntity<String> request){
        return String.valueOf(request.getUrl());
    }

    @GetMapping("/video")
    public static String videoGet(){
        return "videoTemplate";
    }
    @PostMapping("/video")
    public ResponseEntity<byte[]> videoPOst(@RequestParam("video")MultipartFile video){
        return prepareContent(video);
    }
}
