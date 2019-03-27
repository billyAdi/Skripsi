<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
    <head>
        <?php $this->load->view('admin/templates/head', array('title' => 'Admin Page', 'extra_css' => array('bootstrap-theme.min'))); ?>
    </head>
    <body>
        <div class="container">
            <?php $this->load->view('admin/templates/flashmessage'); ?>
            <p>This page is restricted. Click the button below to continue.</p>
            <a class="btn btn-default" href="<?= $authURL ?>">Login with Google</a>
        </div>
        <?php $this->load->view('admin/templates/scripts'); ?>
    </body>
</html>