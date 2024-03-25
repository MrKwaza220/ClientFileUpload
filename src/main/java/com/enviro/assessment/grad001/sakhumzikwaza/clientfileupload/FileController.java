package com.enviro.assessment.grad001.sakhumzikwaza.clientfileupload;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class FileController {

    @Autowired
    private DocumentRepository repo;

    // Handles GET request to "/" and returns the home page
    @GetMapping("/")
    public String viewHomePage(Model model){
        // Retrieve all documents from the repository
        List<Document> listDocs = repo.findAll();
        // Add the list of documents to the model
        model.addAttribute("listDocs", listDocs);
        // Return the name of the HTML page to display
        return "home";
    }

    // Handles file upload POST request and redirects to the home page
    @PostMapping("upload")
    public String uploadFile(@RequestParam("client_files") MultipartFile multipartFile,
                             RedirectAttributes ra) throws IOException {
        // Clean the file name to prevent path traversal attacks
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        // Create a new Document object to save the uploaded file details
        Document document = new Document();
        document.setName(fileName);
        document.setContent(multipartFile.getBytes());
        document.setSize(multipartFile.getSize());
        document.setUploadTime(new Date());

        // Save the document to the repository
        repo.save(document);

        // Add a success message to be displayed on the redirected page
        ra.addFlashAttribute("message", "The file uploaded successfully");
        // Redirect to the home page after file upload
        return "redirect:/";
    }

    // Handles file download GET request based on document ID
    @GetMapping("/download")
    public void downloadfile(@Param("id") Long id, HttpServletResponse response) throws Exception {
        // Retrieve the document from the repository based on the provided ID
        Optional<Document> result = repo.findById(id);
        // Check if the document exists in the repository
        if (!result.isPresent()) {
            // If document does not exist, throw an exception
            throw new Exception("Could not find document with ID: " + id);
        }

        // Retrieve the document object
        Document document = result.get();
        // Set the response content type for file download
        response.setContentType("application/octet-stream");
        // Set the response header for file download with the document name
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + document.getName();
        response.setHeader(headerKey, headerValue);

        // Write the document content to the response output stream
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(document.getContent());
        outputStream.close();
    }
}
