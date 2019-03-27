<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?>
<?php if (isset($_SESSION['info'])): ?>
    <div class="alert alert-info">
        <?= strip_tags($_SESSION['info']) ?>
    </div>
<?php endif; ?>
<?php if (isset($_SESSION['error'])): ?>
    <div class="alert alert-danger">
        <?= strip_tags($_SESSION['error']) ?>
    </div>
<?php endif; ?>