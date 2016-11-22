# 实体表Grid组件使用指南

##1、功能概述

grid就是一个表格组件
{@简单表格.jpg 表格}

## 2、表格的创建

    @example
    var data = NS.util.DataConverter.entitysToStandards(headerData);
                var grid = new NS.grid.Grid({
                    plugins : [new NS.grid.plugin.CellEditor(),
                                new NS.grid.plugin.HeaderQuery()],
                    data : data
                });

