package com.example.myapplication;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.databases.AppDatabase;
import com.example.myapplication.entities.ToDoEntity;
import com.example.myapplication.viewModels.ToDoViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TodoItemTest {
    // FOR DATA
    private AppDatabase database;
    private ToDoViewModel toDoViewModel;
    // DATA SET FOR TEST
    private static long USER_ID = 1;
    private static ToDoEntity NEW_ITEM_PLACE_TO_VISIT = new ToDoEntity(1,"Visite cet endroit de rêve !","dream" );
    private static ToDoEntity NEW_ITEM_IDEA = new ToDoEntity(2,"On pourrait faire du chien de traîneau ?", "je sais");
    private static ToDoEntity NEW_ITEM_RESTAURANTS = new ToDoEntity(3,"Ce restaurant à l'air sympa","tres sympa" );


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }
    private static ToDoEntity USER_DEMO = new ToDoEntity(1,"my note","description");


    @Test
    public void insertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.toDoDao().insert(USER_DEMO);
        // TEST
        ToDoEntity user = LiveDataTestUtil.getValue(this.database.toDoDao().getTodo(USER_ID));
        assertTrue(user.getNoteText().equals(USER_DEMO.getNoteText()) && user.getId() == USER_ID);
    }

//    @Test
//    public void getItemsWhenNoItemInserted() throws InterruptedException {
//        // TEST
//        List<ToDoEntity> items = LiveDataTestUtil.getValue(this.database.toDoDao().getAll());
//        assertTrue(items.isEmpty());
//    }

//    @Test
//    public void insertAndGetItems() throws InterruptedException {
//        // BEFORE : Adding demo user & demo items
//
//        this.database.userDao().createUser(USER_DEMO);
//        this.database.itemDao().insertItem(NEW_ITEM_PLACE_TO_VISIT);
//        this.database.itemDao().insertItem(NEW_ITEM_IDEA);
//        this.database.itemDao().insertItem(NEW_ITEM_RESTAURANTS);
//
//        // TEST
//        List<Item> items = LiveDataTestUtil.getValue(this.database.itemDao().getItems(USER_ID));
//        assertTrue(items.size() == 3);
//    }
@Test
public void insertAndUpdateItem() throws InterruptedException {
    // BEFORE : Adding demo user & demo items. Next, update item added & re-save it
    this.database.toDoDao().insert(USER_DEMO);
    ToDoEntity itemAdded = LiveDataTestUtil.getValue(this.database.toDoDao().getTodo(USER_ID));
    itemAdded.setNoteDescription("hey");
    this.database.toDoDao().update(itemAdded);

    //TEST
    ToDoEntity user = LiveDataTestUtil.getValue(this.database.toDoDao().getTodo(USER_ID));
    assertTrue(user.getNoteDescription().equals("hey"));
}

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.
        this.database.toDoDao().insert(USER_DEMO);
        ToDoEntity itemAdded = LiveDataTestUtil.getValue(this.database.toDoDao().getTodo(USER_ID));
        this.database.toDoDao().delete(itemAdded);

        //TEST
        ToDoEntity items = LiveDataTestUtil.getValue(this.database.toDoDao().getTodo(USER_ID));
        assertTrue(items == null);
    }
    @Test
    public void deleteNonExistingToDO() throws InterruptedException {
        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.


       ToDoEntity toDoEntity = new ToDoEntity(5,"my note","description");
        this.database.toDoDao().delete(toDoEntity);
        //TEST
        ToDoEntity items = LiveDataTestUtil.getValue(this.database.toDoDao().getTodo(USER_ID));
        assertTrue(items == null);
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }
}
