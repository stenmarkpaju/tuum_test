--delete existing entries
delete from account;
delete from balance;

--add into account
insert into account(account_id, country, customer_id)
values ('17820d41-fb37-4ae7-89f8-34191f12f3f1', 'Estonia', 'customer0');

insert into account(account_id, country, customer_id)
values ('a92c5c9d-8ee7-444b-82fd-33b0bec0266a', 'Estonia', 'customer1');

insert into account(account_id, country, customer_id)
values ('30fc2ec7-ae74-42bd-8d18-cbe1fa13d613', 'Estonia', 'customer2');

--add into balance

insert into balance(account_id, available_funds, currency)
values ('17820d41-fb37-4ae7-89f8-34191f12f3f1', '20.0', 'EUR');

insert into balance(account_id, available_funds, currency)
values ('17820d41-fb37-4ae7-89f8-34191f12f3f1', '40.0', 'GBP');

insert into balance(account_id, available_funds, currency)
values ('a92c5c9d-8ee7-444b-82fd-33b0bec0266a', '50.0', 'USD');

insert into balance(account_id, available_funds, currency)
values ('30fc2ec7-ae74-42bd-8d18-cbe1fa13d613', '100.0', 'SEK');
