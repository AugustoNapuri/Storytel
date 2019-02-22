/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  augus
 * Created: 20/02/2019
 */
SET DATABASE SQL SYNTAX PGS TRUE;
SET DATABASE SQL SIZE FALSE;
DROP TABLE IF EXISTS message;

CREATE TABLE message (

    id SERIAL PRIMARY KEY,
    text VARCHAR NOT NULL,
    username VARCHAR NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edited BOOLEAN NOT NULL DEFAULT FALSE
);

