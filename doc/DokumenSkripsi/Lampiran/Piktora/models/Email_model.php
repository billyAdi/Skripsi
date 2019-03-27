<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Email_model extends CI_Model {

    public function send_email($email, $name, $subject, $message, $debug = FALSE) {
        if ($debug === TRUE) {
            echo $message;
            exit();
        }
        $this->load->config('secrets');
        $config = $this->config->item('email-config');
        $this->load->library('email', $config);
        $this->email->set_newline("\r\n");
        $this->email->set_crlf("\r\n");
        $this->email->from("piktora@mailgun.dnartworks.com.au", "PIKTORA");
        $this->email->to("hello@piktora.com");
        $this->email->subject($subject);
        $this->email->message($message);
        if (!$this->email->send()) {
            throw new Exception("Sorry. There was system error when trying to sent your email. Please try again later.");
        }
    }

}