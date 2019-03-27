<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
    <head>
        <?php $this->load->view('admin/templates/head', array('title' => 'Dashboard')); ?>
    </head>
    <body>
        <div class="container">
            <?php $this->load->view('admin/templates/navbar', array('active' => 'dashboard')); ?>
            <?php $this->load->view('admin/templates/flashmessage'); ?>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Konfigurasi</h3>
                </div>
                <div class="panel-body">
                    <form action="/admin/dashboard" method="post">
                        <?php foreach ($configurations as $config): ?>
                            <div class="form-group">
                                <label for="<?= $config->id ?>"><?= $config->name ?></label>
                                <input class="form-control" id="<?= $config->id ?>" name="<?= $config->id ?>" value="<?= $config->value ?>" <?= $config->inputAttributes ?>>
                            </div>
                        <?php endforeach; ?>
                        <button type="submit" class="btn btn-default">Update</button>
                    </form>
                </div>
            </div>
        </div>
        <?php $this->load->view('admin/templates/scripts'); ?>
    </body>
</html>