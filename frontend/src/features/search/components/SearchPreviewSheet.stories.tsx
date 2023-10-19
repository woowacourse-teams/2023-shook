import searchedSingerPreview from '@/mocks/fixtures/searchedSingerPreview.json';
import SearchPreviewSheet from './SearchPreviewSheet';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: SearchPreviewSheet,
  title: 'search/SearchPreviewSheet',
} satisfies Meta<typeof SearchPreviewSheet>;

export default meta;
type Story = StoryObj<typeof SearchPreviewSheet>;

export const Default: Story = {
  render: () => <SearchPreviewSheet result={searchedSingerPreview} endSearch={() => {}} />,
};
