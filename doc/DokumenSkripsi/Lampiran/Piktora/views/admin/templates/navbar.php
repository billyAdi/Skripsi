<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!--navbar-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Piktora Admin</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li <?= $active === 'dashboard' ? 'class="active"' : '' ?>><a href="/admin/dashboard">Dashboard</a></li>
                <li <?= $active === 'home' ? 'class="active"' : '' ?>><a href="/admin/home">Home</a></li>
                <li <?= $active === 'projects' ? 'class="active"' : '' ?>><a href="/admin/projects">Projects</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/admin/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav><!--/navbar-->