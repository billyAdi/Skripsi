<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!--scripts-->
<script src="/assets/js/jquery.min.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<?php if (isset($extra_js)): ?>
    <?php foreach ($extra_js as $js): ?>
        <script src="/assets/js/<?= $js ?>.js"></script>
    <?php endforeach; ?>
<?php endif; ?>
<script src="/assets/js/script.js"></script>
<!--/scripts-->