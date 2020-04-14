package application.view.register;

import application.dao.UserDao;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class registerController {
    private UserDao userDao = new UserDao();

    @FXML
    private JFXTextField uname;
    @FXML
    private JFXTextField tx_email;
    @FXML
    private JFXPasswordField passwd1;
    @FXML
    private JFXPasswordField passwd2;
    @FXML
    private Text txt_message;
    @FXML
    private RadioButton role1;
    @FXML
    private RadioButton role2;

    /**
     * 注册中心返回登录界面按钮监听
     * @param event
     */
    @FXML
    public void ReturnLoginButtonAction(ActionEvent event) {
        closeStage();
        loadRegister();
    }

    /**
     * 注册中心创建用户按钮监听
     * @param event
     */
    @FXML
    public void HandelCreateUserButtonAction(ActionEvent event){
        String username = uname.getText();
        String email = tx_email.getText();
        String password1 = passwd1.getText();
        String password2 = passwd2.getText();

        int is_staff = 0;
        if (role1.isSelected()){//普通用户
            is_staff=0;
        }else if (role2.isSelected()){//职员
            is_staff=1;
        }

        if (userDao.getTemplate()==null){
            txt_message.setText("数据库未连接");
            return;
        }else if (username.isEmpty()){
            txt_message.setText("用户名不能为空");
            return;
        }else if(email.isEmpty()) {
            txt_message.setText("邮箱不能为空");
            return;
        }else if (password1.isEmpty() || password2.isEmpty()){
            txt_message.setText("密码不能为空");
            return;
        }else if (!password1.equals(password2)){
            txt_message.setText("两次密码不相同");
            return;
        }
        if (userDao.searchUser(username)){
            txt_message.setText("当前用户已经存在");
            return;
        }else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date time = df.parse(df.format(new Date()));//获取当前系统时间
                userDao.adduser(username, password1, email, null, null, is_staff, 1, 0, time, time);
                txt_message.setText("注册成功请返回登录");
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 关闭当前注册窗口
     */
    private void closeStage() {
        ((Stage) uname.getScene().getWindow()).close();
    }

    /**
     * 加载注册窗口
     */
    void loadRegister() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/application/view/login/login.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("图书进销系统");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException ex) {

        }
    }


}
