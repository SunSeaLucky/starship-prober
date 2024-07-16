<script setup>
import { ref } from 'vue';

import AppMenuItem from './AppMenuItem.vue';

const adminUserName = ref(true);
const model = ref([
    {
        label: '首页',
        items: [{ label: '主面板', icon: 'pi pi-fw pi-home', to: '/' }]
    },
    {
        label: '星舰管理',
        items: [
            // { label: '概览', icon: 'pi pi-fw pi-align-justify', to: '/uikit/formlayout' },
            { label: '星舰集群', icon: 'pi pi-fw pi-sitemap', to: '/uikit/input' },
            { label: '我的星舰', icon: 'pi pi-fw pi-slack', to: '/uikit/invalidstate' },
            { label: '舰载武器', icon: 'pi pi-fw pi-prime', to: '/uikit/floatlabel' }
            // { label: 'Button', icon: 'pi pi-fw pi-mobile', to: '/uikit/button', class: 'rotated-icon' },
            // { label: 'Table', icon: 'pi pi-fw pi-table', to: '/uikit/table' },
            // { label: 'List', icon: 'pi pi-fw pi-list', to: '/uikit/list' },
            // { label: 'Tree', icon: 'pi pi-fw pi-share-alt', to: '/uikit/tree' },
            // { label: 'Panel', icon: 'pi pi-fw pi-tablet', to: '/uikit/panel' },
            // { label: 'Media', icon: 'pi pi-fw pi-image', to: '/uikit/media' },
            // { label: 'Menu', icon: 'pi pi-fw pi-bars', to: '/uikit/menu', preventExact: true },
            // { label: 'Message', icon: 'pi pi-fw pi-comment', to: '/uikit/message' },
            // { label: 'File', icon: 'pi pi-fw pi-file', to: '/uikit/file' },
            // { label: 'Chart', icon: 'pi pi-fw pi-chart-bar', to: '/uikit/charts' },
            // { label: 'Misc', icon: 'pi pi-fw pi-circle', to: '/uikit/misc' }
        ]
    },
    {
        label: '指挥官信息管理',
        items: [
            { label: '个人信息', icon: 'pi pi-fw pi-eye', to: '/blocks', badge: 'NEW' },
            // { label: '指挥官管理', icon: 'pi pi-user', to: '/uikit/overlay' }
        ]
    },
    {
        label: '星际联邦法案',
        items: [
            { label: '星际联邦宪章与记忆', icon: 'pi pi-fw pi-file', to: '/utilities/icons' }
            // { label: '星际联邦各星系法案', icon: 'pi pi-fw pi-file-edit', to: '/documentation' },
        ]
    }

]);
</script>

<template>
    <ul class="layout-menu">
        <template v-for="(item, i) in model" :key="item">
            <div v-if="adminUserName">
                <app-menu-item v-if="!item.separator" :item="item" :index="i"></app-menu-item>
                <li v-if="item.separator" class="menu-separator"></li>
            </div>
        </template>
        <!--        <li>-->
        <!--            <a href="https://www.primefaces.org/primeblocks-vue/#/" target="_blank">-->
        <!--                <img src="/layout/images/banner-primeblocks.png" alt="Prime Blocks" class="w-full mt-3" />-->
        <!--            </a>-->
        <!--        </li>-->
    </ul>
</template>

<style lang="scss" scoped></style>
