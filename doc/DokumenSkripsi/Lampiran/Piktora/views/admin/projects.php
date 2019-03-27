<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
    <head>
        <?php $this->load->view('admin/templates/head', array('title' => 'Projects')); ?>
    </head>
    <body>
        <div class="container">
            <?php $this->load->view('admin/templates/navbar', array('active' => 'projects')); ?>
            <?php $this->load->view('admin/templates/flashmessage'); ?>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form method="POST" action="/admin/projects/add">
                        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> Tambah Project</button>
                    </form>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Daftar Project</h3>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Judul</th>
                                <th>Tentang Klien</th>
                                <th>Aksi</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php foreach ($projects as $project): ?>
                                <tr>
                                    <td><?= $project->id ?></td>
                                    <td><?= $project->title ?></td>
                                    <td><?= $project->aboutClient ?></td>
                                    <td>
                                        <a href="/admin/projects/edit/<?= $project->id ?>"><span class="glyphicon glyphicon-pencil"></span> Edit</a> |
                                        <a href="/admin/projects/remove/<?= $project->id ?>?type=project"><span class="glyphicon glyphicon-erase"></span> Hapus</a>
                                    </td>
                                </tr>                        
                            <?php endforeach; ?>                            
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <?php $this->load->view('admin/templates/scripts'); ?>
    </body>
</html>