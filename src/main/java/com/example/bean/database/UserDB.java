package com.example.bean.database;

import com.example.bean.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDB {

    public static List<User> users = new ArrayList<>(List.of(
            new User(1, "Bùi Hiên", "phucnhan20022022@gmail.com", "0344005816", "Tỉnh Thái Bình", null, "111"),
            new User(2, "Nguyễn Thu Hằng", "phucnhan20022022@gmail.com", "0123456789", "Tỉnh Nam Định", null, "222"),
            new User(3, "Bùi Phương Loan", "phucnhan20022022@gmail.com", "0123456789", "Tỉnh Hưng Yên", null, "333")
    ));
}
