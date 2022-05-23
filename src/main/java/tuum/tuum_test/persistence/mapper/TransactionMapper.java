package tuum.tuum_test.persistence.mapper;

import java.util.List;
import java.util.UUID;
import org.apache.ibatis.annotations.*;
import tuum.tuum_test.persistence.model.Transaction;

@Mapper
public interface TransactionMapper {

    @Select(
            "SELECT transaction_id, amount, currency, transaction_direction, description FROM transaction t where t.account_id = #{accountId}")
    @Results(
            value = {
                @Result(column = "transaction_id", property = "transactionId"),
                @Result(column = "amount", property = "amount"),
                @Result(column = "currency", property = "currency"),
                @Result(column = "transaction_direction", property = "transactionDirection"),
                @Result(column = "description", property = "description"),
            })
    List<Transaction> findTransactionsByAccountId(@Param("accountId") UUID accountId);

    @Insert(
            "INSERT INTO transaction(account_id, transaction_id, amount, currency, transaction_direction, description) "
                    + " VALUES (#{accountId}, #{transactionId}, #{amount}, #{currency}, #{transactionDirection}, #{description})")
    void insertTransaction(Transaction transaction);
}
