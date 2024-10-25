package com.nhnacademy.jdbc.bank.service.impl;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.exception.AccountAreadyExistException;
import com.nhnacademy.jdbc.bank.exception.AccountNotFoundException;
import com.nhnacademy.jdbc.bank.exception.BalanceNotEnoughException;
import com.nhnacademy.jdbc.bank.repository.AccountRepository;
import com.nhnacademy.jdbc.bank.repository.impl.AccountRepositoryImpl;
import com.nhnacademy.jdbc.bank.service.BankService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.Optional;

@Slf4j
public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository;

    public BankServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(Connection connection, long accountNumber){
        //todo#11 계좌-조회
        Optional<Account> account = accountRepository.findByAccountNumber(connection,accountNumber);
        Account account1 = account.get();
        log.debug("{}",account1.getAccountNumber());
        return account1;
    }

    @Override
    public void createAccount(Connection connection, Account account){

        //todo#12 계좌-등록

        if(isExistAccount(connection,account.getAccountNumber())){
            throw new AccountAreadyExistException(account.getAccountNumber());
        }
        log.debug("{}",account.getAccountNumber());
        int result = accountRepository.save(connection,account);
        log.debug("{}",result);

        if(result != 1){
            throw new RuntimeException("Account not created");
        }

    }

    @Override
    public boolean depositAccount(Connection connection, long accountNumber, long amount){
        //todo#13 예금, 계좌가 존재하는지 체크 -> 예금실행 -> 성공 true, 실패 false;

        boolean result = isExistAccount(connection,accountNumber);
        log.debug("depost isExist: " + result);
        if(result){
            accountRepository.deposit(connection,accountNumber,amount);
            return true;
        }
        throw new AccountNotFoundException(accountNumber);

    }

    @Override
    public boolean withdrawAccount(Connection connection, long accountNumber, long amount){
        //todo#14 출금, 계좌가 존재하는지 체크 ->  출금가능여부 체크 -> 출금실행, 성공 true, 실폐 false 반환

        boolean result = isExistAccount(connection,accountNumber);
        log.debug("withdraw isExist: " + result);
        if(!result){
            throw new AccountNotFoundException(accountNumber);
        }
        long balance = accountRepository.findByAccountNumber(connection,accountNumber).get().getBalance();
        if(balance > amount){
            accountRepository.withdraw(connection,accountNumber,amount);
            return true;
        } else {
            throw new BalanceNotEnoughException(accountNumber);
        }

    }

    @Override
    public void transferAmount(Connection connection, long accountNumberFrom, long accountNumberTo, long amount){
        //todo#15 계좌 이체 accountNumberFrom -> accountNumberTo 으로 amount만큼 이체
        if(!isExistAccount(connection,accountNumberFrom)){
            throw new AccountNotFoundException(accountNumberFrom);
        }
        if(!isExistAccount(connection,accountNumberTo)){
            throw new AccountNotFoundException(accountNumberTo);
        }
        Optional<Account> accountFromOptional = accountRepository.findByAccountNumber(connection,accountNumberFrom);
        if(accountFromOptional.isEmpty()){
            throw new AccountNotFoundException(accountNumberFrom);
        }
        Optional<Account> accountToOptional = accountRepository.findByAccountNumber(connection,accountNumberTo);
        if(accountToOptional.isEmpty()){
            throw new AccountNotFoundException(accountNumberTo);
        }

        Account accountFrom = accountFromOptional.get();

        if(!accountFrom.isWithdraw(amount)){
            throw new BalanceNotEnoughException(accountNumberFrom);
        }

        int resultwith = accountRepository.withdraw(connection,accountNumberFrom,amount);

        if(resultwith<1){
            throw new RuntimeException("withdraw failed");
        }

        int resultdepo = accountRepository.deposit(connection,accountNumberTo,amount);

        if(resultdepo <1){
            throw new RuntimeException("deposit failed");
        }




    }

    @Override
    public boolean isExistAccount(Connection connection, long accountNumber){
        //todo#16 Account가 존재하면 true , 존재하지 않다면 false
        int count = accountRepository.countByAccountNumber(connection, accountNumber);
        return count > 0;

    }

    @Override
    public void dropAccount(Connection connection, long accountNumber) {
        //todo#17 account 삭제
        if(accountRepository.countByAccountNumber(connection,accountNumber) < 1){
            throw new AccountNotFoundException(accountNumber);
        }
        int result = accountRepository.deleteByAccountNumber(connection,accountNumber);

        if(result != 1){
            throw new RuntimeException("Account not dropped");
        }

    }

}