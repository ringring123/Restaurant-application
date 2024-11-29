package com.example.qlnhahang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qlnhahang.Class.DailyMenu;
import com.example.qlnhahang.Class.Employees;
import com.example.qlnhahang.Class.MenuItems;
import com.example.qlnhahang.Class.Tables;
import com.example.qlnhahang.Class.User;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLNhaHang.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_USER = "user";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String TABLE_EMPLOYEES = "employees";
    private static final String EMPLOYEE_ID = "employee_id";
    private static final String FULL_NAME = "full_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String POSITION = "position";
    private static final String SALARY = "salary";

    private static final String TABLE_TABLES = "tables";
    private static final String TABLE_ID = "table_id";
    private static final String TABLE_NUMBER = "table_number";
    private static final String CAPACITY = "capacity";
    private static final String STATUS = "status";
    private static final String TABLE_DATE = "table_date";

    private static final String TABLE_MENU_ITEMS = "menu_items";
    private static final String MENU_ITEM_ID = "menu_item_id";
    private static final String MENU_ITEM_NAME = "menu_item_name";
    private static final String MENU_ITEM_DESCRIPTION = "menu_item_description";
    private static final String MENU_ITEM_PRICE = "menu_item_price";
    private static final String MENU_ITEM_IMAGE = "image";

    private static final String TABLE_DAILY_MENU = "daily_menu";
    private static final String DAILY_MENU_ID = "daily_menu_id";
    private static final String MENU_DATE = "menu_date";

    private static final String TABLE_DAILY_MENU_ITEMS = "daily_menu_items";
    private static final String DAILY_MENU_ITEM_ID = "daily_menu_item_id";
    private static final String DAILY_MENU_ID_FK = "daily_menu_id";
    private static final String MENU_ITEM_ID_FK = "menu_item_id";

    private static final String TABLE_TABLE_MENU_ITEMS = "table_menu_items";
    private static final String TABLE_MENU_ITEM_ID = "table_menu_item_id";
    private static final String TABLE_MENU_ITEM_TABLE_ID = "table_id";
    private static final String TABLE_MENU_ITEM_MENU_ITEM_ID = "menu_item_id";
    private static final String TABLE_MENU_ITEM_QUANTITY = "quantity";
    private static final String TABLE_MENU_ITEM_ORDER_DATE = "order_date";

    private static final String TABLE_REVENUE = "revenue";
    private static final String REVENUE_DATE = "revenue_date";
    private static final String REVENUE_AMOUNT = "revenue_amount";

    // Constructor
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng người dùng
        String createTableUser = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT)", TABLE_USER, USERNAME, PASSWORD);
        db.execSQL(createTableUser);

        // Tạo bảng nhân viên
        String createTableEmployees = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s TEXT, %s TEXT, %s TEXT, %s DECIMAL)", TABLE_EMPLOYEES, EMPLOYEE_ID, FULL_NAME, PHONE_NUMBER, POSITION, SALARY);
        db.execSQL(createTableEmployees);

        // Tạo bảng bàn ăn
        String createTableTables = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s INTEGER, %s INTEGER, %s TEXT, %s TEXT)", TABLE_TABLES, TABLE_ID, TABLE_NUMBER, CAPACITY, STATUS, TABLE_DATE);
        db.execSQL(createTableTables);

        // Tạo bảng món ăn
        String createTableMenuItems = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s TEXT, %s TEXT, %s REAL, %s TEXT)", TABLE_MENU_ITEMS, MENU_ITEM_ID, MENU_ITEM_NAME, MENU_ITEM_DESCRIPTION, MENU_ITEM_PRICE, MENU_ITEM_IMAGE);
        db.execSQL(createTableMenuItems);

        // Tạo bảng thực đơn hàng ngày
        String createTableDailyMenu = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s TEXT UNIQUE)", TABLE_DAILY_MENU, DAILY_MENU_ID, MENU_DATE);
        db.execSQL(createTableDailyMenu);

        // Tạo bảng liên kết món ăn với thực đơn hàng ngày
        String createTableDailyMenuItems = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s INTEGER, %s INTEGER, FOREIGN KEY (%s) REFERENCES %s(%s), FOREIGN KEY (%s) REFERENCES %s(%s))", TABLE_DAILY_MENU_ITEMS, DAILY_MENU_ITEM_ID, DAILY_MENU_ID_FK, MENU_ITEM_ID_FK, DAILY_MENU_ID_FK, TABLE_DAILY_MENU, DAILY_MENU_ID, MENU_ITEM_ID_FK, TABLE_MENU_ITEMS, MENU_ITEM_ID);
        db.execSQL(createTableDailyMenuItems);

        // Tạo bảng liên kết bàn ăn với món ăn
        String createTableTableMenuItems = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s INTEGER, %s INTEGER, %s INTEGER, %s DATETIME, FOREIGN KEY (%s) REFERENCES %s(%s), FOREIGN KEY (%s) REFERENCES %s(%s))", TABLE_TABLE_MENU_ITEMS, TABLE_MENU_ITEM_ID, TABLE_MENU_ITEM_TABLE_ID, TABLE_MENU_ITEM_MENU_ITEM_ID, TABLE_MENU_ITEM_QUANTITY, TABLE_MENU_ITEM_ORDER_DATE, TABLE_MENU_ITEM_TABLE_ID, TABLE_TABLES, TABLE_ID, TABLE_MENU_ITEM_MENU_ITEM_ID, TABLE_MENU_ITEMS, MENU_ITEM_ID);
        db.execSQL(createTableTableMenuItems);

        // Tạo bảng doanh thu
        String createTableRevenue = String.format("CREATE TABLE %s (%s DATETIME PRIMARY KEY, %s REAL)", TABLE_REVENUE, REVENUE_DATE, REVENUE_AMOUNT);
        db.execSQL(createTableRevenue);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu cần thiết và tạo lại bảng mới
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_MENU_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TABLE_MENU_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVENUE);
        onCreate(db);
    }

    public int findUser(User u) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE username=?";
        Cursor cursor = db.rawQuery(query, new String[]{u.getUsername()});

        if (cursor.moveToFirst()) {
            String foundPassword = cursor.getString(1);
            if (foundPassword.equals(u.getPassword())) {
                cursor.close();
                db.close();
                return 1;  // Người dùng được tìm thấy và mật khẩu khớp
            } else {
                cursor.close();
                db.close();
                return -1; // Người dùng được tìm thấy nhưng mật khẩu không khớp
            }
        } else {
            cursor.close();
            db.close();
            return 0; // Người dùng không được tìm thấy
        }
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public ArrayList<Employees> getAllEmployees() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Employees> employeesList = new ArrayList<>();
        String query = "SELECT * FROM employees";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int employeeId = cursor.getInt(0);
                String fullName = cursor.getString(1);
                String phoneNumber = cursor.getString(2);
                String position = cursor.getString(3);
                String salary = cursor.getString(4);
                Employees employee = new Employees(employeeId, fullName, phoneNumber, position, salary);
                employeesList.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employeesList;
    }

    public void addEmployee(Employees employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_ID, employee.getEmployeeId());
        values.put(FULL_NAME, employee.getFullName());
        values.put(PHONE_NUMBER, employee.getPhoneNumber());
        values.put(POSITION, employee.getPosition());
        values.put(SALARY, employee.getSalary());
        db.insert(TABLE_EMPLOYEES, null, values);
        db.close();
    }

    public void deleteEmployee(int employeeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEES, EMPLOYEE_ID + "=?", new String[]{String.valueOf(employeeId)});
        db.close();
    }

    public void updateEmployee(Employees employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMPLOYEE_ID, employee.getEmployeeId());
        values.put(FULL_NAME, employee.getFullName());
        values.put(PHONE_NUMBER, employee.getPhoneNumber());
        values.put(POSITION, employee.getPosition());
        values.put(SALARY, employee.getSalary());
        db.update(TABLE_EMPLOYEES, values, EMPLOYEE_ID + "=?", new String[]{String.valueOf(employee.getEmployeeId())});
        db.close();
    }

    public void AddMenuItem(MenuItems menuItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MENU_ITEM_NAME, menuItem.getName());
        values.put(MENU_ITEM_DESCRIPTION, menuItem.getDescription());
        values.put(MENU_ITEM_PRICE, menuItem.getPrice());
        values.put(MENU_ITEM_IMAGE, menuItem.getImage());
        db.insert(TABLE_MENU_ITEMS, null, values);
        db.close();
    }

    public void updateMenuItem(MenuItems menuItems) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MENU_ITEM_ID, menuItems.getMenuItemId());
        values.put(MENU_ITEM_NAME, menuItems.getName());
        values.put(MENU_ITEM_DESCRIPTION, menuItems.getDescription());
        values.put(MENU_ITEM_PRICE, menuItems.getPrice());
        values.put(MENU_ITEM_IMAGE, menuItems.getImage());
        db.update(TABLE_MENU_ITEMS, values, MENU_ITEM_ID + "=?", new String[]{String.valueOf(menuItems.getMenuItemId())});
        db.close();
    }

    public void deleteMenuItem(int menuItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENU_ITEMS, MENU_ITEM_ID + "=?", new String[]{String.valueOf(menuItemId)});
        db.close();
    }

    public ArrayList<MenuItems> getAllMenuItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MenuItems> menuItemsList = new ArrayList<>();
        String query = "SELECT * FROM menu_items";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                double price = cursor.getDouble(3);
                String image = cursor.getString(4);
                MenuItems menuItem = new MenuItems(id, name, price, description, image);
                menuItemsList.add(menuItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return menuItemsList;
    }

    public ArrayList<DailyMenu> getAllDailyMenus() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DailyMenu> dailyMenus = new ArrayList<>();
        String query = "SELECT * FROM daily_menu";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                DailyMenu dailyMenu = new DailyMenu(id, date);
                dailyMenus.add(dailyMenu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dailyMenus;
    }

    public long addDailyMenuWithItems(String menuDate, ArrayList<MenuItems> menuItems) {
        SQLiteDatabase db = this.getWritableDatabase();
        long dailyMenuId = -1;
        db.beginTransaction(); // Bắt đầu một giao dịch

        try {
            // Chèn dữ liệu vào bảng daily_menu với INSERT OR ROLLBACK
            ContentValues dailyMenuValues = new ContentValues();
            dailyMenuValues.put(MENU_DATE, menuDate);
            dailyMenuId = db.insertOrThrow(TABLE_DAILY_MENU, null, dailyMenuValues);

            // Nếu chèn vào bảng daily_menu thành công, tiếp tục chèn vào bảng daily_menu_items
            for (MenuItems menuItem : menuItems) {
                ContentValues dailyMenuItemValues = new ContentValues();
                dailyMenuItemValues.put(DAILY_MENU_ID_FK, dailyMenuId);
                dailyMenuItemValues.put(MENU_ITEM_ID_FK, menuItem.getMenuItemId());
                long result = db.insert(TABLE_DAILY_MENU_ITEMS, null, dailyMenuItemValues);

                if (result == -1) {
                    // Nếu một trong các chèn vào bảng daily_menu_items thất bại, rollback giao dịch
                    db.endTransaction();
                    db.close();
                    return -1; // Trả về -1 để báo lỗi
                }
            }

            db.setTransactionSuccessful(); // Đánh dấu giao dịch thành công
        } catch (SQLiteConstraintException e) {
            // Xử lý ngoại lệ khi ngày đã tồn tại do ràng buộc UNIQUE
            e.printStackTrace();
            dailyMenuId = -1;
        } catch (Exception e) {
            e.printStackTrace();
            dailyMenuId = -1;
        } finally {
            db.endTransaction(); // Kết thúc giao dịch (commit hoặc rollback)
            db.close(); // Đóng cơ sở dữ liệu
        }

        return dailyMenuId; // Trả về ID của daily_menu nếu thành công hoặc -1 nếu thất bại
    }

    public int getCountOfMenuItemsByDate(String menuDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_DAILY_MENU_ITEMS + " dmi " + "JOIN " + TABLE_DAILY_MENU + " dm ON dmi." + DAILY_MENU_ID_FK + " = dm." + DAILY_MENU_ID + " WHERE dm." + MENU_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{menuDate});

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<MenuItems> getMenuItemsForDate(int tableid, String menuDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MenuItems> menuItemsList = new ArrayList<>();

        // Truy vấn để lấy DAILY_MENU_ID cho ngày cụ thể
        String query = "SELECT " + DAILY_MENU_ID + " FROM " + TABLE_DAILY_MENU + " WHERE " + MENU_DATE + " = ?";
        Cursor cursor = null;
        Cursor itemsCursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{menuDate});

            if (cursor.moveToFirst()) {
                int dailyMenuId = cursor.getInt(0);

                // Truy vấn để lấy danh sách món ăn cho DAILY_MENU_ID
                String itemsQuery = "SELECT " + TABLE_MENU_ITEMS + "." + MENU_ITEM_ID + ", " +
                        TABLE_MENU_ITEMS + "." + MENU_ITEM_NAME + ", " +
                        TABLE_MENU_ITEMS + "." + MENU_ITEM_DESCRIPTION + ", " +
                        TABLE_MENU_ITEMS + "." + MENU_ITEM_PRICE + ", " +
                        TABLE_MENU_ITEMS + "." + MENU_ITEM_IMAGE +
                        " FROM " + TABLE_MENU_ITEMS +
                        " INNER JOIN " + TABLE_DAILY_MENU_ITEMS +
                        " ON " + TABLE_MENU_ITEMS + "." + MENU_ITEM_ID + " = " + TABLE_DAILY_MENU_ITEMS + "." + MENU_ITEM_ID_FK +
                        " WHERE " + TABLE_DAILY_MENU_ITEMS + "." + DAILY_MENU_ID_FK + " = ?";
                itemsCursor = db.rawQuery(itemsQuery, new String[]{String.valueOf(dailyMenuId)});

                while (itemsCursor.moveToNext()) {
                    int itemId = itemsCursor.getInt(0);
                    String itemName = itemsCursor.getString(1);
                    String itemDescription = itemsCursor.getString(2);
                    double itemPrice = itemsCursor.getDouble(3);
                    String itemImage = itemsCursor.getString(4);
                    int quantity = getQuantityForMenuItem(tableid, itemId);
                    MenuItems menuItem = new MenuItems(itemId, itemName, itemPrice, itemDescription, itemImage, quantity);
                    menuItemsList.add(menuItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi nếu có
        } finally {
            if (cursor != null) cursor.close();
            if (itemsCursor != null) itemsCursor.close();
            db.close(); // Đảm bảo đóng kết nối DB
        }

        return menuItemsList;
    }
    public int getQuantityForMenuItem(int tableId, int menuItemId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int quantity = 0;
        String query = "SELECT " + TABLE_MENU_ITEM_QUANTITY +
                " FROM " + TABLE_TABLE_MENU_ITEMS +
                " WHERE " + TABLE_MENU_ITEM_TABLE_ID + " = ?" +
                " AND " + TABLE_MENU_ITEM_MENU_ITEM_ID + " = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(tableId), String.valueOf(menuItemId)});
            if (cursor.moveToFirst()) {
                quantity = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi nếu có
        } finally {
            if (cursor != null) cursor.close();
            db.close(); // Đảm bảo đóng kết nối DB
        }

        return quantity;
    }



    public void updateDailyMenu(String menuDate, ArrayList<MenuItems> menuItems) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Truy vấn để lấy ID của menu theo ngày
            String query = "SELECT " + DAILY_MENU_ID + " FROM " + TABLE_DAILY_MENU + " WHERE " + MENU_DATE + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{menuDate});

            int dailyMenuId;
            if (cursor.moveToFirst()) {
                dailyMenuId = cursor.getInt(0);
            } else {
                // Nếu không có menu cho ngày đó, thêm một bản ghi mới vào bảng daily_menu
                ContentValues values = new ContentValues();
                values.put(MENU_DATE, menuDate);
                dailyMenuId = (int) db.insert(TABLE_DAILY_MENU, null, values);
            }
            cursor.close();

            // Xóa các món ăn hiện tại cho ngày đó
            String deleteQuery = "DELETE FROM " + TABLE_DAILY_MENU_ITEMS + " WHERE " + DAILY_MENU_ID_FK + " = ?";
            db.execSQL(deleteQuery, new String[]{String.valueOf(dailyMenuId)});

            // Thêm các món ăn mới vào bảng daily_menu_items
            for (MenuItems item : menuItems) {
                ContentValues itemValues = new ContentValues();
                itemValues.put(DAILY_MENU_ID_FK, dailyMenuId);
                itemValues.put(MENU_ITEM_ID_FK, item.getMenuItemId());
                db.insert(TABLE_DAILY_MENU_ITEMS, null, itemValues);
            }

            // Hoàn tất giao dịch
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void deleteDailyMenu(String menuDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction(); // Bắt đầu một giao dịch

        try {
            // Xóa dữ liệu trong bảng daily_menu_items liên quan đến ngày thực đơn
            String query = "DELETE FROM " + TABLE_DAILY_MENU_ITEMS + " WHERE " + DAILY_MENU_ID_FK + " IN (SELECT " + DAILY_MENU_ID + " FROM " + TABLE_DAILY_MENU + " WHERE " + MENU_DATE + " = ?)";
            db.execSQL(query, new String[]{menuDate});

            // Xóa dữ liệu trong bảng daily_menu
            db.delete(TABLE_DAILY_MENU, MENU_DATE + " = ?", new String[]{menuDate});

            db.setTransactionSuccessful(); // Đánh dấu giao dịch thành công
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction(); // Kết thúc giao dịch (commit hoặc rollback)
            db.close(); // Đóng cơ sở dữ liệu
        }
    }

    public ArrayList<Tables> getAllTables() {
        ArrayList<Tables> tablesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn tất cả dữ liệu từ bảng tables
        String query = "SELECT * FROM " + TABLE_TABLES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ cursor
                int tableId = cursor.getInt(0);
                int tableNumber = cursor.getInt(1);
                int capacity = cursor.getInt(2);
                String status = cursor.getString(3);
                String tableDate = cursor.getString(4);

                // Tạo đối tượng Tables và thêm vào danh sách
                Tables table = new Tables(tableId, tableNumber, capacity, status, tableDate);
                tablesList.add(table);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return tablesList;
    }

    public int addTable(Tables table) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = -1;

        // Check if the table number already exists
        String checkQuery = "SELECT COUNT(*) FROM " + TABLE_TABLES + " WHERE " + TABLE_NUMBER + " = ?";
        Cursor cursor = db.rawQuery(checkQuery, new String[]{String.valueOf(table.getTableNumber())});

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();

            if (count > 0) {
                // Table number already exists
                db.close();
                return -1; // Return -1 to indicate the table number already exists
            }
        }

        // Insert the new table if it does not exist
        ContentValues values = new ContentValues();
        values.put(TABLE_NUMBER, table.getTableNumber());
        values.put(CAPACITY, table.getCapacity());
        values.put(STATUS, table.getStatus());
        values.put(TABLE_DATE, table.getTabledate());

        // Insert the new record and get the row ID
        long rowId = db.insert(TABLE_TABLES, null, values);
        db.close();

        if (rowId != -1) {
            result = (int) rowId; // Return the row ID if insertion was successful
        }
        return result;
    }

    public void deleteTable(int tableId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TABLES, TABLE_ID + "=?", new String[]{String.valueOf(tableId)});
        db.close();
    }

    public void updateTable(Tables table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_ID, table.getTableId());
        values.put(TABLE_NUMBER, table.getTableNumber());
        values.put(CAPACITY, table.getCapacity());
        values.put(STATUS, table.getStatus());
        values.put(TABLE_DATE, table.getTabledate());
        db.update(TABLE_TABLES, values, TABLE_ID + "=?", new String[]{String.valueOf(table.getTableId())});
        db.close();
    }

    public void updateMenuItemQuantity(int tableId, int menuItemId, int quantity, String orderDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra xem mục đã tồn tại hay chưa
        String query = "SELECT * FROM " + TABLE_TABLE_MENU_ITEMS +
                " WHERE " + TABLE_MENU_ITEM_TABLE_ID + " = ? AND " +
                TABLE_MENU_ITEM_MENU_ITEM_ID + " = ? AND " +
                TABLE_MENU_ITEM_ORDER_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(tableId), String.valueOf(menuItemId), orderDate});

        ContentValues values = new ContentValues();
        values.put(TABLE_MENU_ITEM_TABLE_ID, tableId);
        values.put(TABLE_MENU_ITEM_MENU_ITEM_ID, menuItemId);
        values.put(TABLE_MENU_ITEM_QUANTITY, quantity);
        values.put(TABLE_MENU_ITEM_ORDER_DATE, orderDate);

        if (cursor.getCount() > 0) {
            // Nếu mục đã tồn tại, cập nhật số lượng
            db.update(TABLE_TABLE_MENU_ITEMS, values,
                    TABLE_MENU_ITEM_TABLE_ID + " = ? AND " +
                            TABLE_MENU_ITEM_MENU_ITEM_ID + " = ? AND " +
                            TABLE_MENU_ITEM_ORDER_DATE + " = ?",
                    new String[]{String.valueOf(tableId), String.valueOf(menuItemId), orderDate});
        } else {
            // Nếu mục chưa tồn tại, thêm mới
            db.insert(TABLE_TABLE_MENU_ITEMS, null, values);
        }

        cursor.close();
        db.close();
    }

    public double getRevenueForTableAndDate(int tableId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalRevenue = 0;

        // Truy vấn để tính tổng doanh thu cho table_id và ngày cụ thể
        String query = "SELECT SUM(tmi." + TABLE_MENU_ITEM_QUANTITY + " * mi." + MENU_ITEM_PRICE + ") AS total_revenue " +
                "FROM " + TABLE_TABLE_MENU_ITEMS + " tmi " +
                "INNER JOIN " + TABLE_MENU_ITEMS + " mi ON tmi." + TABLE_MENU_ITEM_MENU_ITEM_ID + " = mi." + MENU_ITEM_ID + " " +
                "WHERE tmi." + TABLE_MENU_ITEM_TABLE_ID + " = ? AND tmi." + TABLE_MENU_ITEM_ORDER_DATE + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(tableId), date});

        if (cursor.moveToFirst()) {
            totalRevenue = cursor.getDouble(0); // Lấy giá trị tổng doanh thu
        }
        cursor.close();
        db.close();
        return totalRevenue;
    }


    public void addRevenue(String date, double revenue) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra doanh thu đã tồn tại cho ngày cụ thể
        String checkQuery = "SELECT " + REVENUE_AMOUNT + " FROM " + TABLE_REVENUE + " WHERE " + REVENUE_DATE + " = ?";
        Cursor cursor = db.rawQuery(checkQuery, new String[]{date});

        if (cursor.moveToFirst()) {
            // Doanh thu đã tồn tại, cập nhật doanh thu
            double existingRevenue = cursor.getDouble(0);
            ContentValues values = new ContentValues();
            values.put(REVENUE_AMOUNT, existingRevenue + revenue); // Cộng doanh thu mới vào doanh thu hiện tại
            db.update(TABLE_REVENUE, values, REVENUE_DATE + " = ?", new String[]{date});
        } else {
            // Doanh thu chưa tồn tại, chèn doanh thu mới
            ContentValues values = new ContentValues();
            values.put(REVENUE_DATE, date);
            values.put(REVENUE_AMOUNT, revenue);
            db.insert(TABLE_REVENUE, null, values);
        }

        cursor.close();
        db.close();
    }

    public void deleteTableMenuItems(int tableId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TABLE_MENU_ITEMS, TABLE_MENU_ITEM_TABLE_ID + " = ?", new String[]{String.valueOf(tableId)});
        ContentValues values = new ContentValues();
        values.put(STATUS, "Trống");
        db.update(TABLE_TABLES, values, TABLE_ID + " = ?", new String[]{String.valueOf(tableId)});
        db.close();
    }

    public double getTotalForDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalRevenue = 0;

        // Truy vấn để lấy tổng doanh thu cho ngày cụ thể
        String query = "SELECT " + REVENUE_AMOUNT + " FROM " + TABLE_REVENUE + " WHERE " + REVENUE_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date});

        // Kiểm tra xem con trỏ có di chuyển được đến hàng đầu tiên không
        if (cursor.moveToFirst()) {
            // Lấy giá trị tổng doanh thu từ cột đầu tiên
            totalRevenue = cursor.getDouble(0);
        }

        cursor.close();
        db.close();
        return totalRevenue;
    }




}
