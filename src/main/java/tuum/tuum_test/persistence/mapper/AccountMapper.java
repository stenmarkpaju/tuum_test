package tuum.tuum_test.persistence.mapper;

import java.util.List;
import java.util.UUID;
import org.apache.ibatis.annotations.*;
import tuum.tuum_test.persistence.model.Account;
import tuum.tuum_test.persistence.model.Balance;

@Mapper
public interface AccountMapper {
    @Select("SELECT account_id, customer_id FROM account a")
    @Results(
            value = {
                @Result(column = "account_id", property = "accountId"),
                @Result(column = "customer_id", property = "customerId"),
                @Result(
                        property = "balance",
                        column = "account_id",
                        javaType = List.class,
                        many = @Many(select = "getAllBalances"))
            })
    List<Account> findAll();

    @Select("SELECT available_funds, currency FROM balance b where b.account_id = #{accountId}")
    @Results(
            value = {
                @Result(property = "availableFunds", column = "available_funds"),
                @Result(property = "currency", column = "currency")
            })
    List<Balance> getAllBalances(@Param("accountId") UUID accountId);

    @Update(
            "UPDATE balance b SET available_funds=#{balance.availableFunds} WHERE b.account_id=#{accountId} AND b.currency=#{balance.currency}")
    void updateBalanceByAccountIdAndCurrency(@Param("accountId") UUID accountId, Balance balance);

    @Select("SELECT account_id, customer_id FROM account a where a.account_id = #{accountId}")
    @Results(
            value = {
                @Result(column = "account_id", property = "accountId"),
                @Result(column = "customer_id", property = "customerId"),
                @Result(
                        property = "balance",
                        column = "account_id",
                        javaType = List.class,
                        many = @Many(select = "getAllBalances"))
            })
    Account findAccountByAccountId(@Param("accountId") UUID accountId);

    @Insert(
            "INSERT INTO account(account_id, customer_id, country) "
                    + " VALUES (#{accountId}, #{customerId}, #{country})")
    void insertAccount(Account account);

    @Insert(
            "INSERT INTO balance(account_id, available_funds, currency) "
                    + " VALUES (#{accountId}, #{balance.availableFunds}, #{balance.currency})")
    void insertBalance(UUID accountId, Balance balance);
}
