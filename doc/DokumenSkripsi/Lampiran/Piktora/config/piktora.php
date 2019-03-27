<?php

defined('BASEPATH') OR exit('No direct script access allowed');

$config['captcha-settings'] = array(
    'word' => random_string('alnum', 8),
    'font_path' => './assets/fonts/Montserrat-Regular.otf',
    'img_width' => 150,
    'img_height' => 50,
    'expiration' => 7200,
    'word_length' => 8,
    'font_size' => 16,
    'img_id' => 'captcha',
    'pool' => '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ',
    'colors' => array(
        'background' => array(255, 255, 255),
        'border' => array(255, 255, 255),
        'text' => array(0, 0, 0),
        'grid' => array(119, 115, 114)
    ),
    'img_path' => './assets/img/captcha/',
    'img_url' => '/assets/img/captcha/'
);
