package application.dao;

import application.model.Book;
import application.model.auth.User;
import application.utils.JdbcUtils;
import application.utils.MD5Util;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * 用户数据层操作
 */
public class UserDao {
    private Connection conn;
    private PreparedStatement pstmt;
    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());

    /**
     * 新增用户
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param first_name 姓
     * @param last_name 名
     * @param is_staff 是否为职员
     * @param is_active 账户是否激活
     * @param is_superuser 是否是超级管理员
     * @param date_joined 注册时间
     * @param last_join 上一次登录时间(新用户注册与注册时间相等)
     */
    public void adduser(String username, String password, String email, String first_name, String last_name, int is_staff, int is_active, int is_superuser, Date date_joined, Date last_join) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String md5_password = MD5Util.getEncryptedPwd(password);
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sql, null, username, md5_password, email, first_name, last_name, is_staff, is_active, is_superuser, date_joined, last_join);
    }

    /**
     * 登录验证成功后获取user
     * @param username
     * @return 当前登录的对象
     */
    public User getLoginUser(String username){
        String sql = "select * from user where username=?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        return user;
    }


    /**
     * 获取所有用户
     * @return 用户集合
     */
    public List<User> getAllUser(){
        String sql = "select * from user";
        List<User> list = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return list;
    }

    /**
     * 会员中心更改用户资料
     * @param user
     */
    public void updateUserMessage(User user){
        String sql = "update user set username=?,password=?,email=?,first_name=?,last_name=?";
        template.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getFirst_name(), user.getLast_name());
    }

    /**
     * 更新用户密码
     * @param user
     */
    public void updateUserPassword(User user){
        String sql = "update user set password=? where username=?";
        template.update(sql, user.getPassword(), user.getUsername());
    }

    /**
     * 通过username查询用户
     * @param username 用户名
     * @return 是否存在
     */
    public boolean searchUser(String username){
        boolean flag;
        String sql = "select * from user where username=?";
        try {
            template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            flag = true;
        } catch (Exception e){
            flag = false;
        }
        return flag;
    }

    /**
     * 通过id删除用户
     * @param user
     */
    public void delUser(User user){
        String sql = "delete from user where id=?";
        template.update(sql,user.getId());
    }

    /**
     * 登录验证
     */
    public boolean checkPassword(String username, String passwd){
        boolean flag = false;
        String sql = "select * from user where username=?";
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            if (MD5Util.validPassword(passwd, user.getPassword())) {
                flag = true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return flag;
    }


    public JdbcTemplate getTemplate() {
        return template;
    }
}
