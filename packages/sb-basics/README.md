# Spring boot basics

## Overview
 This project was developed with the aim to get familiarity with springboot syntax and the basics of the whole ecosystem. This is a simple CRUD app that allows users to add, edit, delete and list customers on a database.

---------------

### Requirements:
 - java 17;
 - docker & docker copose;

### Spining up:
I am using originally pnpm, but you can use your prefered package manager such as npm or yarn.

`pnpm docker-up sb-basics`

`pnpm serve sb-basics`

### Endpoints:

List all, add new
http://localhost:6543/api/v1/customers

update, delete
http://localhost:6543/api/v1/customers/{id}