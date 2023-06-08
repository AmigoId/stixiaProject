package com.example.stixia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
/*
//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 9
        private val DATABASE_NAME = "MyDatabase"
        private val TABLE_CONTACTS = "AuthorTable"
        private val KEY_ID = "id"
        private val KEY_IMAGEID = "imageId"
        private val KEY_LOGIN = "login"
        private val KEY_PASSWORD = "password"
        private val KEY_BIO = "bio"
        private val KEY_COUNTSUB = "countSub"

        private val TABLE_CONTACTS_POEM = "PoemTable"
        private val KEY_ID_POEM = "id"
        private val KEY_IMAGEID_POEM = "imageId"
        private val KEY_NAME_POEM = "name"
        private val KEY_TEXT_POEM = "text"
        private val KEY_BIO_POEM = "bio"
        private val KEY_COUNTVIEW_POEM = "countView"
        private val KEY_COUNTLIKE_POEM = "countLike"
        private val KEY_idAuthor_POEM = "idAuthor"

        private val TABLE_CONTACTS_AUTHORPOEM = "AuthorPoemTable"
        private val KEY_ID_AUTHORPOEM = "id"
        private val KEY_IDAUTHOR_AUTHORPOEM = "idAuthor"
        private val KEY_IDPOEM_AUTHORPOEM = "idPoem"

        private val KEY_ID_AUTHORSUB = "id"
        private val TABLE_CONTACTS_AUTHORSUB = "AuthorSubTable"
        private val KEY_IDAUTHOR_AUTHORSUB = "idAuthor"
        private val KEY_IDSUB_AUTHORSUB = "idSub"


        private val KEY_ID_AUTHORLIKE = "id"
        private val TABLE_CONTACTS_AUTHORLIKE = "AuthorLikeTable"
        private val KEY_IDAUTHOR_AUTHORLIKE = "idAuthor"
        private val KEY_IDLIKE_AUTHORLIKE = "idLike"



    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE1 =("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_IMAGEID + " INTEGER, "
                + KEY_LOGIN + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_BIO + " TEXT, " + KEY_COUNTSUB + " INTEGER " + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE1)
        val CREATE_CONTACTS_TABLE2 =("CREATE TABLE " + TABLE_CONTACTS_POEM + "("
                + KEY_ID_POEM + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_IMAGEID_POEM + " INTEGER, "
                + KEY_NAME_POEM + " TEXT, " + KEY_TEXT_POEM + " TEXT, " + KEY_BIO_POEM + " TEXT, " + KEY_COUNTLIKE_POEM + " INTEGER, "
                + KEY_COUNTVIEW_POEM + " INTEGER, " + KEY_idAuthor_POEM + " INTEGER" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE2)
        val CREATE_CONTACTS_TABLE3 =("CREATE TABLE " + TABLE_CONTACTS_AUTHORPOEM + "("
                + KEY_ID_AUTHORPOEM + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_IDAUTHOR_AUTHORPOEM + " INTEGER, "
                + KEY_IDPOEM_AUTHORPOEM + " INTEGER"+ ")")
        db?.execSQL(CREATE_CONTACTS_TABLE3)

        val CREATE_CONTACTS_TABLE4 =("CREATE TABLE " + TABLE_CONTACTS_AUTHORSUB + "("
                + KEY_ID_AUTHORSUB + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_IDAUTHOR_AUTHORSUB + " INTEGER, "
                + KEY_IDSUB_AUTHORSUB + " INTEGER"+ ")")
        db?.execSQL(CREATE_CONTACTS_TABLE4)

        val CREATE_CONTACTS_TABLE5 =("CREATE TABLE " + TABLE_CONTACTS_AUTHORLIKE + "("
                + KEY_ID_AUTHORLIKE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_IDAUTHOR_AUTHORLIKE + " INTEGER, "
                + KEY_IDLIKE_AUTHORLIKE + " INTEGER"+ ")")
        db?.execSQL(CREATE_CONTACTS_TABLE5)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_POEM)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_AUTHORPOEM)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_AUTHORSUB)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_AUTHORLIKE)
        onCreate(db)
    }


    //method to insert data
    fun addAuthor(author: Author):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_IMAGEID, author.imageId) // EmpModelClass Name
        contentValues.put(KEY_LOGIN,author.login ) // EmpModelClass Phone
        contentValues.put(KEY_PASSWORD, author.password)
        contentValues.put(KEY_BIO, author.bio)
        contentValues.put(KEY_COUNTSUB, author.countSub)

        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun addPoem(poem1: poem):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_IMAGEID_POEM, poem1.imageId) // EmpModelClass Name
        contentValues.put(KEY_NAME_POEM,poem1.name ) // EmpModelClass Phone
        contentValues.put(KEY_TEXT_POEM, poem1.text)
        contentValues.put(KEY_BIO_POEM, poem1.bio)
        contentValues.put(KEY_COUNTVIEW_POEM, poem1.countView)
        contentValues.put(KEY_COUNTLIKE_POEM, poem1.countLike)
        contentValues.put(KEY_idAuthor_POEM, poem1.idAuthor)

        // Inserting Row
        val success = db.insert(TABLE_CONTACTS_POEM, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun addAuthorPoem(author: Author,poem1: poem):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_IDAUTHOR_AUTHORPOEM, author.id)
        contentValues.put(KEY_IDPOEM_AUTHORPOEM, poem1.id)

        // Inserting Row
        val success = db.insert(TABLE_CONTACTS_AUTHORPOEM, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun addAuthorSub(author1: Author,author2: Author):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_IDAUTHOR_AUTHORSUB, author1.id)
        contentValues.put(KEY_IDSUB_AUTHORSUB, author2.id)

        // Inserting Row
        val success = db.insert(TABLE_CONTACTS_AUTHORSUB, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun addAuthorLike(author: Author, poem: poem):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_IDAUTHOR_AUTHORLIKE, author.id)
        contentValues.put(KEY_IDLIKE_AUTHORLIKE, poem.id)

        // Inserting Row
        val success = db.insert(TABLE_CONTACTS_AUTHORLIKE, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }



    //method to read data
    fun viewAuthor():List<Author>{
        val AuthorList = ArrayList<Author>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var imageId: Int
        var login: String
        var password: String
        var bio: String
        var countSub: Int
        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                imageId = cursor.getInt(cursor.getColumnIndexOrThrow("imageId"))
                login = cursor.getString(cursor.getColumnIndexOrThrow("login"))
                password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                bio = cursor.getString(cursor.getColumnIndexOrThrow("bio"))
                countSub = cursor.getInt(cursor.getColumnIndexOrThrow("countSub"))
                val author= Author(id = id, imageId = imageId, login = login, password = password, bio = bio, countSub=countSub)
                AuthorList.add(author)
            } while(cursor.moveToNext())
        }
        return AuthorList
    }

    //method to read data
    fun viewPoem():List<poem>{
        val poemList = ArrayList<poem>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS_POEM"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var imageId: Int
        var name: String
        var text: String
        var bio: String
        var countLike: Int
        var countView: Int
        var idAuthor: Int

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                imageId = cursor.getInt(cursor.getColumnIndexOrThrow("imageId"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                text = cursor.getString(cursor.getColumnIndexOrThrow("text"))
                bio = cursor.getString(cursor.getColumnIndexOrThrow("bio"))
                countLike = cursor.getInt(cursor.getColumnIndexOrThrow("countLike"))
                countView = cursor.getInt(cursor.getColumnIndexOrThrow("countView"))
                idAuthor = cursor.getInt(cursor.getColumnIndexOrThrow("idAuthor"))
                val poem2= poem(id = id, imageId = imageId, name = name, text = text, bio = bio, countLike=countLike, countView = countView, idAuthor=idAuthor)
                poemList.add(poem2)
            } while(cursor.moveToNext())
        }
        return poemList
    }



    //method to read data
    fun viewAuthorPoem():List<AuthorPoem>{
        val AuthorPoemList = ArrayList<AuthorPoem>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS_AUTHORPOEM"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var idAuthor: Int
        var idPoem: Int

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                idPoem = cursor.getInt(cursor.getColumnIndexOrThrow("idPoem"))
                idAuthor = cursor.getInt(cursor.getColumnIndexOrThrow("idAuthor"))
                val AuthorPoem2= AuthorPoem(id = id, idPoem = idPoem, idAuthor = idAuthor)
                AuthorPoemList.add(AuthorPoem2)
            } while(cursor.moveToNext())
        }
        return AuthorPoemList
    }

    //method to read data
    fun viewAuthorSub():List<AuthorSub>{
        val AuthorSubList = ArrayList<AuthorSub>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS_AUTHORSUB"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var idAuthor: Int
        var idSub: Int

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                idAuthor = cursor.getInt(cursor.getColumnIndexOrThrow("idAuthor"))
                idSub = cursor.getInt(cursor.getColumnIndexOrThrow("idSub"))
                val AuthorSub2= AuthorSub(id = id, idAuthor = idAuthor, idSub = idSub)
                AuthorSubList.add(AuthorSub2)
            } while(cursor.moveToNext())
        }
        return AuthorSubList
    }

    fun viewAuthorLike():List<AuthorLike>{
        val AuthorLikeList = ArrayList<AuthorLike>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS_AUTHORLIKE"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var idAuthor: Int
        var idLike: Int

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                idAuthor = cursor.getInt(cursor.getColumnIndexOrThrow("idAuthor"))
                idLike = cursor.getInt(cursor.getColumnIndexOrThrow("idLike"))
                val AuthorSub2= AuthorLike(id = id, idAuthor = idAuthor, idPoemLike = idLike)
                AuthorLikeList.add(AuthorSub2)
            } while(cursor.moveToNext())
        }
        return AuthorLikeList
    }

    //method to update data
    fun updateAuthor(author: Author, index: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, index)
        contentValues.put(KEY_IMAGEID, author.imageId)
        contentValues.put(KEY_LOGIN,author.login )
        contentValues.put(KEY_PASSWORD, author.password)
        contentValues.put(KEY_BIO,author.bio )
        contentValues.put(KEY_COUNTSUB, author.countSub)
        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+index,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun updateAuthorPoem(index: Int, author: Author,poem1: poem ):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_IDAUTHOR_AUTHORPOEM, author.id)
        contentValues.put(KEY_IDPOEM_AUTHORPOEM, poem1.id)
        // Updating Row
        val success = db.update(TABLE_CONTACTS_AUTHORPOEM, contentValues,"id="+index,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun updateAuthorSub(index: Int, author1: Author,author2: Author):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_IDAUTHOR_AUTHORPOEM, author1.id)
        contentValues.put(KEY_IDPOEM_AUTHORPOEM, author2.id)
        // Updating Row
        val success = db.update(TABLE_CONTACTS_AUTHORSUB, contentValues,"id="+index,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun updateAuthorLike(index: Int, author: Author,poem1: poem):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_IDAUTHOR_AUTHORPOEM, author.id)
        contentValues.put(KEY_IDPOEM_AUTHORPOEM, poem1.id)
        // Updating Row
        val success = db.update(TABLE_CONTACTS_AUTHORLIKE, contentValues,"id="+index,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    //method to update data
    fun updatePoem(poem1: poem, index: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID_POEM, index)
        contentValues.put(KEY_IMAGEID_POEM, poem1.imageId)
        contentValues.put(KEY_NAME_POEM,poem1.name )
        contentValues.put(KEY_TEXT_POEM, poem1.text)
        contentValues.put(KEY_BIO_POEM,poem1.bio )
        contentValues.put(KEY_COUNTVIEW_POEM, poem1.countView)
        contentValues.put(KEY_COUNTLIKE_POEM,poem1.countLike )
        contentValues.put(KEY_idAuthor_POEM, poem1.idAuthor)
        // Updating Row
        val success = db.update(TABLE_CONTACTS_POEM, contentValues,"id="+index,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    //method to delete data
    fun deleteAuthor(author: Author):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, author.id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+author.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun deletePoem(poem1: poem):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, poem1.id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS_POEM,"id="+poem1.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun deleteAuthorPoem(index: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, index) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS_AUTHORPOEM,"id="+index,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun deleteAuthorSUB(index: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, index) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS_AUTHORSUB,"id="+index,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun deleteAuthorLike(index: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, index) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS_AUTHORLIKE,"id="+index,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


}*/