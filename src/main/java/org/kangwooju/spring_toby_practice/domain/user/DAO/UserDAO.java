package org.kangwooju.spring_toby_practice.domain.user.DAO;

import org.kangwooju.spring_toby_practice.Global.Config.ConnectionMaker;
import org.kangwooju.spring_toby_practice.domain.user.Entity.User;
import org.kangwooju.spring_toby_practice.domain.user.Service.DConnectionMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.List;

public class UserDAO {

    private DataSource dataSource;
    private JdbcContext jdbcContext;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(DataSource dataSource,JdbcContext jdbcContext,JdbcTemplate jdbcTemplate){
        this.dataSource = dataSource;
        this.jdbcContext = jdbcContext;
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> userRowMapper =
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user= new User();
                    user.setName(rs.getString("name"));
                    user.setId(rs.getString("id"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            };


    public void add(final User user) throws ClassNotFoundException, SQLException {



        /*
        Connection connection = dataSource.getConnection(); // ConnectionMaker 대신 dataSource 인터페이스 사용

        Class.forName("com.mysql.cj.jdbc.Driver");


        // DB를 연결하는 로직을 분리
        Connection connection = simpleConnectionMaker.makeNewConnection();

        Connection connection = ConnectionMaker.makeConnection();


         */

        /*
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO users(id, name, password) VALUES (?, ?, ?)"
        );

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        connection.close();

         */

        /* 클래스 파일을 생성해야 하는 문제 발생 -> 로컬 클래스를 생성한다.
        // 템플릿 인터페이스를 생성 ( AddStatement ) 하여 모든 로직을 인터페이스에 의존
        StatementStrategy statementStrategy = new AddStatement(user);
        jdbcContextWithStatementStrategy(statementStrategy);
         */


        /*
        // 로컬 클래스로 대체
        class AddStatement implements StatementStrategy {

            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {

                PreparedStatement preparedStatement
                        = connection.prepareStatement("INSERT INTO USERS(id,name,password) VALUES (?,?,?)");
                preparedStatement.setString(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getPassword());

                return preparedStatement;
            }
        }

        StatementStrategy statementStrategy = new AddStatement();
        jdbcContextWithStatementStrategy(statementStrategy);
         */

        /*
        this.jdbcContext.workWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement preparedStatement
                                = connection.prepareStatement("INSERT INTO USERS(id,name,password) VALUES(?,?,?)");
                        preparedStatement.setString(1, user.getId());
                        preparedStatement.setString(2,user.getName());
                        preparedStatement.setString(3,user.getPassword());

                        return preparedStatement;
                    }
                }

        );

         */
        // JdbcTemplate로 대체
        this.jdbcTemplate.update("INSERT INTO USERS(id,name,password) VALUES (?,?,?)"
                ,user.getId(),user.getName(),user.getPassword());
    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        /*
        Class.forName("com.mysql.cj.jdbc.Driver");


        // DB를 연결하는 로직을 분리
       // Connection connection = simpleConnectionMaker.makeNewConnection();

        Connection connection = dataSource.getConnection();


        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM users WHERE id = ?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        connection.close();

        return user;

         */

        return this.jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?", new Object[]{id},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                        return user;
                    }
                });
    }

    // 모든 회원 정보를 가져오는 로직
    public List<User> getAll(){
        return this.jdbcTemplate.query("SELECT * FROM USERS ORDER BY id", this.userRowMapper);
    }


    public void deleteAll() throws SQLException{
        /*

        // 익명 클래스로 변경 -> 템플릿&콜백 패턴
        this.jdbcContext.workWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                        return connection.prepareStatement("DELETE FROM USERS");
                    }
                }
        );
         */

        // excuteSql("DELETE FROM USERS"); // -> excuteSql 메소드를 통해 변하는 SQL 문장을 설정
        // this.jdbcContext.excuteSql("DELETE FROM USERS");
        this.jdbcTemplate.update("DELETE FROM USERS"); // JDBC가 지원하는 Template 사용
    }


    // JdbcTemplate을 이용한 getCount()
    public int getCount1() {

        return this.jdbcTemplate.query(new PreparedStatementCreator() {


                                           @Override
                                           public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                                               return con.prepareStatement("SELECT COUNT(*) FROM USERS");
                                           }
                                       }, new ResultSetExtractor<Integer>() {
                                           @Override
                                           public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                                               rs.next();
                                               return rs.getInt(1);
                                           }
                                       }
                                       /*
                                       1st. PreparedStatement로 쿼리 전달하기
                                       2nd. ResultSetExtractor를 통해 결과를 받기
                                        */
        );

    }



    // JdbcContext 로 변경 -> 주석 변경
    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException{

        /* 3장 템플릿 : try - catch - finally 도입 이유
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("DELETE FROM USERS");
        ps.executeUpdate();

        ps.close();
        c.close();
         */

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();

            /* try - catch에서 DELETE하는 메소드로 책임 이관
            preparedStatement = connection.prepareStatement("DELETE FROM USERS");
             */
            preparedStatement = statementStrategy.makePreparedStatement(connection);
            /* DeleteAllStatement 클래스로 대체 -> Interface를 구체화 하고 , UserDAO는 인터페이스에 의존한다.
            preparedStatement = makeStatement(connection); // makeStatement로 DELETE 메소드를 변경
             */
            preparedStatement.executeUpdate();
        } catch ( SQLException e ){
            throw e;
        } finally {
            if(preparedStatement != null){try {preparedStatement.close();} catch ( SQLException e ){}}
            if(connection != null){try {connection.close();} catch ( SQLException e ){}}

        }
    }

    /*

    // DELETE 작업을 시행하는 메소드를 생성
    private PreparedStatement makeStatement(Connection connection) throws SQLException{
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM USERS");
        return preparedStatement;
    }
     */


    // 템플릿&콜백 에서 변하지 않는 콜백 클래스 생성
    private void excuteSql(final String query) throws SQLException{
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement(query);
            }
        });
    }
}
