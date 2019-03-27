<?php
defined('BASEPATH') or exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
    <head>
        <?php $this->load->view('admin/templates/head', array('title' => 'Projects', 'extra_css' => array('summernote'))); ?>
        <style>
            .thumbnail {
                width: 100%;
            }

            .optional-photo{
                min-height: 300px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <?php $this->load->view('admin/templates/navbar', array('active' => 'projects')); ?>
            <?php $this->load->view('admin/templates/flashmessage'); ?>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Detail Project #<?= $project->id ?></h3>
                </div>
                <div class="panel-body">
                    <form method="POST" action="/admin/projects/update/<?= $project->id ?>">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-sm-8">
                                    <div class="form-group">
                                        <label for="title">Judul</label>
                                        <input type="text" class="form-control" id="title" placeholder="Judul Project" maxlength="256" name="title" value="<?= $project->title ?>"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="products">Produk</label>
                                        <textarea class="form-control" id="products" name="products"><?= $project->products; ?></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="categories">Kategori</label>
                                        <textarea class="form-control" id="categories" name="categories"><?= $project->categories; ?></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="aboutClient">Tentang Klien</label>
                                        <textarea class="form-control" id="aboutClient" name="aboutClient" maxlength="500" rows="5"><?= $project->aboutClient; ?></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="ourConcept">Konsep</label>
                                        <textarea class="form-control" id="ourConcept" name="ourConcept" maxlength="500" rows="5"><?= $project->ourConcept; ?></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="additionalInfo">Info Tambahan</label>
                                        <textarea class="form-control" id="additionalInfo" name="additionalInfo"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Submit</button>
                                    <a class="btn btn-default" href="/admin/projects"><span class="glyphicon glyphicon-remove"></span> Cancel</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Foto Project #<?= $project->id ?></h3>
                </div>
                <div class="panel-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-sm-3">
                                <label>Foto Utama</label>
                                <div class="input-group">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <?php if ($project->mainphoto === null): ?>
                                                Tidak ada foto utama.
                                            <?php else: ?>
                                                <img src="<?= $project->mainphoto ?>" alt="Main Photo" class='thumbnail'/>
                                            <?php endif; ?>
                                            <button class='btn btn-default' type="button" data-toggle="modal" data-target="#uploadmainphotomodal"><span class="glyphicon glyphicon-upload"></span> Upload JPG</button>
                                            <a class='btn btn-default' href="/admin/projects/remove/<?= $project->id ?>?type=mainphoto"><span class="glyphicon glyphicon-erase"></span> Hapus</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-9">
                                <label>Foto Tambahan</label>
                                <div class='row'>
                                    <div class="input-group">
                                        <?php if (count($project->photos) === 0): ?>
                                            <div class="col-sm-12">Tidak ada foto tambahan.</div>
                                        <?php else: ?>
                                            <?php 
                                                $ct = 1;
                                                foreach ($project->photos as $photo): 
                                            ?>
                                                <div class="optional-photo col-sm-4">
                                                    <a href="/admin/projects/remove/<?= $project->id ?>?type=additionalphoto&additionalid=<?= $ct; ?>">Hapus</a>
                                                    <img src="<?= $photo ?>" alt="Additional Photo" class='thumbnail'/>
                                                </div>
                                            <?php 
                                                $ct++;
                                                endforeach; 
                                            ?>
                                        <?php endif; ?>
                                    </div>
                                </div>
                                <button class='btn btn-default' type="button" data-toggle="modal" data-target="#uploadadditionalphotosmodal"><span class="glyphicon glyphicon-upload"></span> Upload JPG</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="uploadmainphotomodal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form method="POST" action="/admin/projects/upload" enctype="multipart/form-data">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Upload Foto Utama</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="hidden" name="type" value="main"/>
                                <input type="hidden" name="id" value="<?= $project->id ?>"/>
                                <input type="file" name="file"/>
                            </div>
                            <p class="text-info">Hanya menerima file JPEG. Ukuran 200x200 (rasio 1:1).</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Upload</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="uploadadditionalphotosmodal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form method="POST" action="/admin/projects/upload" enctype="multipart/form-data">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Upload Foto Utama</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <input type="hidden" name="type" value="additional"/>
                                <input type="hidden" name="id" value="<?= $project->id ?>"/>
                                <input type="file" name="file"/>
                            </div>
                            <p class="text-info">Hanya menerima file JPEG. Width 770px dan height bebas.</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Upload</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <?php $this->load->view('admin/templates/scripts', array('extra_js' => array('summernote.min'))); ?>
        <script>
            $(function () {
                var summernoteToolbar = [
                    ['insert', ['link', 'hr']],
                    ['style', ['bold', 'italic', 'underline', 'clear']],
                    ['font', ['strikethrough', 'superscript', 'subscript']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['misc', ['fullscreen', 'codeview', 'undo', 'redo', 'help']]
                ];
                $('#additionalInfo').summernote({
                    toolbar: summernoteToolbar,
                    minHeight: 200
                });
                $('#additionalInfo').summernote('code', '<?= $project->additionalInfo === null ? '<div><div><h6>-judul- Website</h6><p><a href="http://piaggio.co.id">judul.co.id</a></p></div><div><h6>-judul- Microsite</h6><p><a href="http://judul.dnartworks.co.id">judul.dnartworks.co.id</a></p></div></div>' : addslashes($project->additionalInfo); ?>');
            });
        </script>
    </body>
</html>
