package com.example.blogapplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelTest {
    private Model model;

    @Before
    public void setUp() {
        model = new Model("Title", "Description", "Author", "Date", "ImageURL", "5", "1", "timestamp");
    }

    @Test
    public void testGetters() {
        assertEquals("Title", model.getTittle());
        assertEquals("Description", model.getDesc());
        assertEquals("Author", model.getAuthor());
        assertEquals("Date", model.getDate());
        assertEquals("ImageURL", model.getImg());
        assertEquals("5", model.getShare_count());
        assertEquals("1", model.getId());
        assertEquals("timestamp", model.getTimestamp());
    }

    @Test
    public void testSetters() {
        model.setTittle("New Title");
        model.setDesc("New Description");
        model.setAuthor("New Author");
        model.setDate("New Date");
        model.setImg("New ImageURL");
        model.setShare_count("10");
        model.setId("2");
        model.setTimestamp("new_timestamp");

        assertEquals("New Title", model.getTittle());
        assertEquals("New Description", model.getDesc());
        assertEquals("New Author", model.getAuthor());
        assertEquals("New Date", model.getDate());
        assertEquals("New ImageURL", model.getImg());
        assertEquals("10", model.getShare_count());
        assertEquals("2", model.getId());
        assertEquals("new_timestamp", model.getTimestamp());
    }
}
