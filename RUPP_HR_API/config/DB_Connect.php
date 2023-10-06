<?php
 
    class DB_Connect {
     
        function __construct() {
             
        }
     
        function __destruct() {

        }
     
        public function connect() {
            require_once 'Config.php';

            $con = new PDO('mysql:host='.DB_HOST.';dbname='.DB_DATABASE.';charset=utf8', DB_USER, DB_PASSWORD);
            $con->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
            $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
     
            return $con;
        }
     
        public function close() {

        }
     
    }
 
?>
