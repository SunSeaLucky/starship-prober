<script setup>
import { useLayout } from '@/layout/composables/layout';
import { computed } from 'vue';

const { layoutConfig } = useLayout();

const logoUrl = computed(() => {
    return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`;
});
</script>

<template>
    <div class="layout-footer">

        <img :src="logoUrl" alt="Logo" height="20" class="mr-2" />
        by
        <span class="font-medium ml-2">中央系统最高权限管理员 SunSeaLucky</span>
        <span class="font-medium ml-2">&copy; 银河管理局备案号：<span class="text-blue-500">火AAAAAAAA</span></span>
    </div>
</template>
<style lang="scss" scoped></style>
