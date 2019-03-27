<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?>
<?php if (isset($_SESSION['success'])): ?>
    <div class="alert alert-success">
        <i class="fa fa-check-circle-o"></i> <?= strip_tags($_SESSION['success']) ?> 
    </div>
<?php endif; ?>

<?php if (isset($_SESSION['info'])): ?>
    <div class="alert alert-info">
        <i class="fa fa-info-circle"></i> <?= strip_tags($_SESSION['info']) ?>
    </div>
<?php endif; ?>

<?php if (isset($_SESSION['warning'])): ?>
    <div class="alert alert-warning">
        <i class="fa fa-exclamation-triangle"></i> <?= strip_tags($_SESSION['warning']) ?>
    </div>
<?php endif; ?>

<?php if (isset($_SESSION['danger'])): ?>
    <div class="alert alert-danger">
        <i class="fa fa-exclamation-circle"></i> <?= strip_tags($_SESSION['danger']) ?>
    </div>
<?php endif; ?>