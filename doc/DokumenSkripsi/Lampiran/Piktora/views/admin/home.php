<?php
defined('BASEPATH') or exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
    <head>
        <?php $this->load->view('admin/templates/head', array('title' => 'Home')); ?>
        <style>
            .thumbnail {
                width: 100%;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <?php $this->load->view('admin/templates/navbar', array('active' => 'home')); ?>
            <?php $this->load->view('admin/templates/flashmessage'); ?>
            
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Foto Home</h3>
                </div>
                <div class="panel-body">
                    <div class="container-fluid">
                        <div class="row">
                            <?php
                                $ct = 1;
                                foreach ($photos as $photo): 
                            ?>
                            <div class="col-sm-3">
                                <a href="/admin/home/remove/<?= $ct; ?>">Hapus</a>
                                <img class="thumbnail" src="<?= $photo?>" alt="Home-Background">
                            </div>
                            <?php 
                                $ct++;
                                endforeach; 
                            ?>
                        </div>
                        <div class="row">
                            <div class="col-sm-3">
                                <button class='btn btn-default' type="button" data-toggle="modal" data-target="#uploadphotomodal"><span class="glyphicon glyphicon-upload"></span> Upload JPG</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="uploadphotomodal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form method="POST" action="/admin/home/upload" enctype="multipart/form-data">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Upload Foto Home</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="file" name="file"/>
                            </div>
                            <p class="text-info">Hanya menerima file JPEG. Ukuran 1600x900. Maksimum 8 Foto.</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Upload</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <?php $this->load->view('admin/templates/scripts'); ?>
    </body>
</html>
