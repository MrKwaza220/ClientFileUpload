package com.enviro.assessment.grad001.sakhumzikwaza.clientfileupload;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// Annotation to specify that this class is a data JPA test
@DataJpaTest
// Annotation to auto-configure the test database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientFileUploadApplicationTests {
    // Autowired annotation to inject DocumentRepository bean
    @Autowired
    private  DocumentRepository repo;

    // Autowired annotation to inject TestEntityManager bean
    @Autowired
    private TestEntityManager entityManager;

    // Test method to insert a document into the database
    @Test
    // Annotation to prevent rollback of transaction after the test
    @Rollback(false)
    public void testInsertDocument() throws IOException {
        // Create a new file object
        File file = new File("");
        // Create a new Document object
        Document document = new Document();
        // Set the name of the document as the file name
        document.setName(file.getName());

        // Read the contents of the file into a byte array
        byte[] bytes = Files.readAllBytes(file.toPath());
        // Set the content of the document as the byte array
        document.setContent(bytes);
        // Get the size of the file and set it as the size of the document
        long fileSize = bytes.length;
        document.setSize(fileSize);
        // Set the upload time of the document as the current date and time
        document.setUploadTime(new Date());

        // Save the document to the repository and get the saved document
        Document saveDoc = repo.save(document);

        // Find the saved document from the entity manager
        Document existDoc = entityManager.find(Document.class, saveDoc.getId());

        // Assert that the size of the existing document matches the size of the file
        assertThat(existDoc.getSize()).isEqualTo(fileSize);

    }

}
