package com.example.blogapplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelTest {

    private Model model;

    @Before
    public void setUp() {
        model = new Model(
                "Sample Title",
                "Sample Description",
                "Author Name",
                "2024-05-23",
                "http://example.com/image.jpg",
                "10",
                "1",
                "1629456789000"
        );
    }

    @Test
    public void testGetTittle() {
        assertEquals("Sample Title", model.getTittle());
    }

    @Test
    public void testSetTittle() {
        model.setTittle("New Title");
        assertEquals("New Title", model.getTittle());
    }

    @Test
    public void testGetDesc() {
        assertEquals("Sample Description", model.getDesc());
    }

    @Test
    public void testSetDesc() {
        model.setDesc("New Description");
        assertEquals("New Description", model.getDesc());
    }

    @Test
    public void testGetAuthor() {
        assertEquals("Author Name", model.getAuthor());
    }

    @Test
    public void testSetAuthor() {
        model.setAuthor("New Author");
        assertEquals("New Author", model.getAuthor());
    }

    @Test
    public void testGetDate() {
        assertEquals("2024-05-23", model.getDate());
    }

    @Test
    public void testSetDate() {
        model.setDate("2024-06-01");
        assertEquals("2024-06-01", model.getDate());
    }

    @Test
    public void testGetImg() {
        assertEquals("http://example.com/image.jpg", model.getImg());
    }

    @Test
    public void testSetImg() {
        model.setImg("http://example.com/new_image.jpg");
        assertEquals("http://example.com/new_image.jpg", model.getImg());
    }

    @Test
    public void testGetShareCount() {
        assertEquals("10", model.getShare_count());
    }

    @Test
    public void testSetShareCount() {
        model.setShare_count("20");
        assertEquals("20", model.getShare_count());
    }

    @Test
    public void testGetId() {
        assertEquals("1", model.getId());
    }

    @Test
    public void testSetId() {
        model.setId("2");
        assertEquals("2", model.getId());
    }

    @Test
    public void testGetTimestamp() {
        assertEquals("1629456789000", model.getTimestamp());
    }

    @Test
    public void testSetTimestamp() {
        model.setTimestamp("1629456790000");
        assertEquals("1629456790000", model.getTimestamp());
    }
}
