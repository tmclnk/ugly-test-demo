package com.prairiegrade.ugly;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestHarness {
    private static final Logger logger = LoggerFactory.getLogger(TestHarness.class);
    private final EntityManager em;
    
    public TestHarness(EntityManager em){
        this.em = em;
    }
    
    public void test(String dir){
        
    }
    
    public void dumpExpected(Class<?> clazz, Object id){
        
    }
    
    void transform(Class<?> clazz, Object id, Appendable out){
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        Object o = em.find(clazz, id);
        Gson gson = builder.create();
        gson.toJson(o, out);
    }
    
}
