package hk.ust.comp3021.utils;

import hk.ust.comp3021.action.SearchPaperAction;
import hk.ust.comp3021.action.SearchPaperAction.SearchPaperKind;
import hk.ust.comp3021.person.User;
import hk.ust.comp3021.resource.Paper;
import hk.ust.comp3021.utils.TestKind;
import hk.ust.comp3021.MiniMendeleyEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestCustomArrayList {
    @Tag(TestKind.PUBLIC)
    @Test
    void testCustomArrayListWithTypePaper() {
    	MiniMendeleyEngine engine = new MiniMendeleyEngine();
        String userID = "User_" + engine.getUsers().size();
        User user = engine.processUserRegister(userID, "testUser", new Date());

        SearchPaperAction action = new SearchPaperAction("Action", user, new Date(),"Thomas Reps", SearchPaperKind.AUTHOR);
        ArrayList<Paper> searchResult = engine.processSearchPaperActionByLambda(user, action);
        
    	CustomArrayList<Paper> customArrayList = new CustomArrayList<>();
    	
    	assertTrue(customArrayList.isEmpty());
    	
    	for (Paper paper : searchResult) {
    		customArrayList.add(paper);
    	}
    	
    	assertEquals(customArrayList.size(), searchResult.size());
    	
    	assertFalse(customArrayList.isEmpty());
    	
    	assertNotEquals(customArrayList.get(0), searchResult.get(2));
    	
    	assertEquals(customArrayList.get(1), searchResult.get(1));
    	
    	assertNull(customArrayList.get(1000));
    	
    	assertTrue(customArrayList.contains(searchResult.get(3)));
    	
    }
    
    @Tag(TestKind.PUBLIC)
    @Test
    void testCustomArrayListWithTypeString() {
    	CustomArrayList<String> customArrayList = new CustomArrayList<>();
    	
    	assertTrue(customArrayList.isEmpty());
    	
    	customArrayList.add("1");
    	customArrayList.add("2");
    	customArrayList.add("3");
    	customArrayList.add("4");
    	customArrayList.add("5");
    	customArrayList.add("6");
    	
    	assertEquals(customArrayList.size(), 6);
    	
    	assertFalse(customArrayList.isEmpty());
    	
    	assertNotEquals(customArrayList.get(0), "22");
    	
    	assertEquals(customArrayList.get(1), "2");
    	
    	assertNull(customArrayList.get(1000));
    	
    	assertTrue(customArrayList.contains("5"));
    	
    	assertFalse(customArrayList.contains("10"));
    	
    }
}